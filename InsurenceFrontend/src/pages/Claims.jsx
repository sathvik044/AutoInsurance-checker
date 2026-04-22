import React, { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useAuth } from '../context/useAuth';
import { claimService, policyService } from '../services/api';
import { Plus, ClipboardList, DollarSign, FileText, CheckCircle, XCircle, Clock, ArrowRight } from 'lucide-react';

const Claims = () => {
  const { user } = useAuth();
  const queryClient = useQueryClient();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [formData, setFormData] = useState({
    policyId: '',
    claimType: 'HEALTH',
    incidentDate: '',
    description: ''
  });

  const { data: claims, isLoading } = useQuery({
    queryKey: ['claims', user.id],
    queryFn: () => (user.role === 'POLICYHOLDER' 
      ? claimService.getClaimsByUserId(user.id) 
      : claimService.getAllClaims()).then(res => res.data),
  });

  const { data: policies } = useQuery({
    queryKey: ['policies', user.id],
    queryFn: () => (user.role === 'POLICYHOLDER' 
      ? policyService.getPoliciesByUserId(user.id) 
      : policyService.getPolicies()).then(res => res.data),
    enabled: user.role === 'POLICYHOLDER'
  });

  const createClaimMutation = useMutation({
    mutationFn: (data) => {
      const incidentDateIso = data.incidentDate ? `${data.incidentDate}T00:00:00` : null;
      return claimService.createClaim({
        policyId: data.policyId ? Number(data.policyId) : null,
        claimantId: user.id,
        claimType: data.claimType,
        description: data.description,
        incidentDate: incidentDateIso,
      }).then(res => res.data);
    },
    onSuccess: () => {
      queryClient.invalidateQueries(['claims']);
      setIsModalOpen(false);
      setFormData({ policyId: '', claimType: 'HEALTH', incidentDate: '', description: '' });
    },
  });

  const actionMutation = useMutation({
    mutationFn: ({ id, action }) => {
      switch (action) {
        case 'submit': return claimService.submitClaim(id);
        case 'verify': return claimService.verifyDocuments(id);
        case 'review': return claimService.moveToReview(id);
        case 'autoValidate': return claimService.autoValidate(id);
        case 'adjusterApprove': return claimService.adjusterApprove(id);
        case 'managerApprove': return claimService.managerApprove(id);
        case 'approve': return claimService.approveClaim(id);
        case 'settle': return claimService.settleClaim(id);
        case 'reject': return claimService.rejectClaim(id);
        default: throw new Error('Invalid action');
      }
    },
    onSuccess: () => queryClient.invalidateQueries(['claims']),
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    createClaimMutation.mutate(formData);
  };

  const getStatusBadge = (status) => {
    const badges = {
      CREATED: 'bg-gray-100 text-gray-700',
      SUBMITTED: 'bg-blue-100 text-blue-700',
      DOCUMENT_VERIFICATION: 'bg-indigo-100 text-indigo-700',
      UNDER_REVIEW: 'bg-yellow-100 text-yellow-700',
      AUTO_VALIDATED: 'bg-teal-100 text-teal-700',
      ADJUSTER_APPROVED: 'bg-green-100 text-green-700',
      MANAGER_APPROVAL: 'bg-green-100 text-green-700',
      APPROVED: 'bg-green-100 text-green-700',
      SETTLED: 'bg-purple-100 text-purple-700',
      REJECTED: 'bg-red-100 text-red-700',
    };
    return badges[status] || 'bg-gray-100 text-gray-700';
  };

  const renderActions = (claim) => {
    const actions = [];
    
    if (user.role === 'POLICYHOLDER' && claim.status === 'CREATED') {
      actions.push({ label: 'Submit', action: 'submit', color: 'bg-blue-600' });
    }

    if (user.role === 'CLAIM_ADJUSTER') {
      if (claim.status === 'SUBMITTED') {
        actions.push({ label: 'Verify Docs', action: 'verify', color: 'bg-blue-600' });
      }

      if (claim.status === 'DOCUMENT_VERIFICATION') {
        actions.push({ label: 'Move to Review', action: 'review', color: 'bg-yellow-600' });
      }

      if (claim.status === 'UNDER_REVIEW') {
        actions.push({ label: 'Auto Validate', action: 'autoValidate', color: 'bg-green-600' });
        actions.push({ label: 'Reject', action: 'reject', color: 'bg-red-600' });
      }

      if (claim.status === 'AUTO_VALIDATED') {
        actions.push({ label: 'Adjuster Approve', action: 'adjusterApprove', color: 'bg-green-600' });
        actions.push({ label: 'Reject', action: 'reject', color: 'bg-red-600' });
      }
    }

    if (user.role === 'CLAIM_MANAGER') {
      if (claim.status === 'ADJUSTER_APPROVED') {
        actions.push({ label: 'Manager Approve', action: 'managerApprove', color: 'bg-green-600' });
        actions.push({ label: 'Reject', action: 'reject', color: 'bg-red-600' });
      }
    }

    if (user.role === 'ADMIN') {
      if (claim.status === 'APPROVED') {
        actions.push({ label: 'Settle', action: 'settle', color: 'bg-purple-600' });
      }
    }

    return actions.map(act => (
      <button
        key={act.action}
        onClick={() => actionMutation.mutate({ id: claim.id, action: act.action })}
        className={`${act.color} text-white text-xs px-3 py-1.5 rounded-md font-medium hover:opacity-90 transition-opacity flex items-center gap-1`}
      >
        {act.label}
        <ArrowRight size={14} />
      </button>
    ));
  };

  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-4 sm:flex-row sm:justify-between sm:items-end">
        <div>
          <h1 className="text-3xl sm:text-4xl font-extrabold tracking-tight text-slate-900">Insurance Claims</h1>
          <p className="text-slate-600">Track and manage insurance claim requests.</p>
        </div>
        {user.role === 'POLICYHOLDER' && (
          <button
            onClick={() => setIsModalOpen(true)}
            className="inline-flex items-center gap-2 rounded-2xl bg-gradient-to-r from-blue-600 via-indigo-600 to-fuchsia-600 px-4 py-3 text-white font-semibold shadow-sm hover:shadow-md transition-all"
          >
            <Plus size={20} />
            File New Claim
          </button>
        )}
      </div>

      <div className="hidden md:block bg-white/80 rounded-2xl border border-slate-200/70 shadow-sm overflow-hidden backdrop-blur">
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="bg-gray-50 border-b">
                <th className="px-6 py-4 text-sm font-semibold text-gray-900">Claim ID</th>
                <th className="px-6 py-4 text-sm font-semibold text-gray-900">Policy ID</th>
                <th className="px-6 py-4 text-sm font-semibold text-gray-900">Type</th>
                <th className="px-6 py-4 text-sm font-semibold text-gray-900">Incident Date</th>
                <th className="px-6 py-4 text-sm font-semibold text-gray-900">Status</th>
                <th className="px-6 py-4 text-sm font-semibold text-gray-900 text-right">Actions</th>
              </tr>
            </thead>
            <tbody className="divide-y">
              {isLoading ? (
                <tr><td colSpan="6" className="px-6 py-12 text-center text-gray-500">Loading claims...</td></tr>
              ) : claims?.length === 0 ? (
                <tr><td colSpan="6" className="px-6 py-12 text-center text-gray-500">No claims found</td></tr>
              ) : claims?.map(claim => (
                <tr key={claim.id} className="hover:bg-gray-50 transition-colors">
                  <td className="px-6 py-4 text-sm font-medium text-gray-900">#{claim.id}</td>
                  <td className="px-6 py-4 text-sm text-gray-600">#{claim.policyId}</td>
                  <td className="px-6 py-4 text-sm text-gray-600 capitalize">{claim.claimType.replace(/_/g, ' ').toLowerCase()}</td>
                  <td className="px-6 py-4 text-sm text-gray-600">{new Date(claim.incidentDate).toLocaleDateString()}</td>
                  <td className="px-6 py-4">
                    <span className={`text-xs font-bold px-2 py-1 rounded-full ${getStatusBadge(claim.status)}`}>
                      {claim.status}
                    </span>
                  </td>
                  <td className="px-6 py-4">
                    <div className="flex justify-end gap-2">
                      {renderActions(claim)}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      <div className="md:hidden grid gap-4">
        {isLoading ? (
          <div className="text-center py-12 text-slate-600">Loading claims...</div>
        ) : claims?.length === 0 ? (
          <div className="text-center py-12 text-slate-600">No claims found</div>
        ) : claims?.map(claim => (
          <div key={claim.id} className="rounded-2xl border border-slate-200/70 bg-white/80 p-4 shadow-sm backdrop-blur">
            <div className="flex items-start justify-between gap-3">
              <div>
                <div className="text-sm font-semibold text-slate-900">Claim #{claim.id}</div>
                <div className="mt-1 text-xs text-slate-500">Policy #{claim.policyId}</div>
              </div>
              <span className={`text-xs font-bold px-2 py-1 rounded-full ${getStatusBadge(claim.status)}`}>
                {claim.status}
              </span>
            </div>
            <div className="mt-3 grid grid-cols-2 gap-3 text-sm">
              <div>
                <div className="text-xs text-slate-500">Type</div>
                <div className="font-semibold text-slate-900 capitalize">{claim.claimType.replace(/_/g, ' ').toLowerCase()}</div>
              </div>
              <div>
                <div className="text-xs text-slate-500">Incident</div>
                <div className="font-semibold text-slate-900">{new Date(claim.incidentDate).toLocaleDateString()}</div>
              </div>
            </div>
            <div className="mt-4 flex flex-wrap gap-2">
              {renderActions(claim)}
            </div>
          </div>
        ))}
      </div>

      {/* New Claim Modal */}
      {isModalOpen && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center p-4 z-50">
          <div className="bg-white/90 backdrop-blur rounded-3xl shadow-2xl max-w-md w-full p-6 border border-white/40">
            <h2 className="text-2xl font-extrabold mb-6 text-slate-900">File New Claim</h2>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Select Policy</label>
                <select
                  value={formData.policyId}
                  onChange={(e) => setFormData({ ...formData, policyId: e.target.value })}
                  className="w-full p-3 rounded-2xl border border-slate-200 bg-white focus:ring-2 focus:ring-blue-500/40 outline-none"
                  required
                >
                  <option value="">Select a policy</option>
                  {policies?.filter(p => p.status === 'ACTIVATE').map(p => (
                    <option key={p.id} value={p.id}>{p.policyType} (ID: {p.id})</option>
                  ))}
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Claim Type</label>
                <select
                  value={formData.claimType}
                  onChange={(e) => setFormData({ ...formData, claimType: e.target.value })}
                  className="w-full p-3 rounded-2xl border border-slate-200 bg-white focus:ring-2 focus:ring-blue-500/40 outline-none"
                >
                  <option value="HEALTH">Health</option>
                  <option value="ACCIDENT">Accident</option>
                  <option value="THEFT">Theft</option>
                  <option value="DAMAGE">Damage</option>
                  <option value="DEATH">Death</option>
                </select>
              </div>
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-slate-700 mb-1">Incident Date</label>
                  <input
                    type="date"
                    value={formData.incidentDate}
                    onChange={(e) => setFormData({ ...formData, incidentDate: e.target.value })}
                    className="w-full p-3 rounded-2xl border border-slate-200 bg-white focus:ring-2 focus:ring-blue-500/40 outline-none"
                    required
                  />
                </div>
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Description</label>
                <textarea
                  value={formData.description}
                  onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                  className="w-full p-3 rounded-2xl border border-slate-200 bg-white focus:ring-2 focus:ring-blue-500/40 outline-none h-28"
                  placeholder="Describe the incident..."
                  required
                ></textarea>
              </div>
              <div className="flex gap-4 pt-4">
                <button
                  type="button"
                  onClick={() => setIsModalOpen(false)}
                  className="flex-1 px-4 py-3 border border-slate-200 rounded-2xl font-semibold text-slate-700 hover:bg-slate-50 transition-colors"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  disabled={createClaimMutation.isPending}
                  className="flex-1 rounded-2xl bg-gradient-to-r from-blue-600 via-indigo-600 to-fuchsia-600 text-white px-4 py-3 font-semibold shadow-sm hover:shadow-md transition-all disabled:opacity-60"
                >
                  {createClaimMutation.isPending ? 'Filing...' : 'File Claim'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default Claims;
