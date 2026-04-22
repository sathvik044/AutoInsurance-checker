import React, { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useAuth } from '../context/useAuth';
import { policyService } from '../services/api';
import { Plus, Shield, Calendar, DollarSign, User } from 'lucide-react';

const Policies = () => {
  const { user } = useAuth();
  const queryClient = useQueryClient();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [formData, setFormData] = useState({
    policyType: 'HEALTH',
    premiumAmount: '',
    startDate: '',
    endDate: '',
  });

  const { data: policies, isLoading } = useQuery({
    queryKey: ['policies', user.id],
    queryFn: () => (user.role === 'POLICYHOLDER' 
      ? policyService.getPoliciesByUserId(user.id) 
      : policyService.getPolicies()).then(res => res.data),
  });

  const createPolicyMutation = useMutation({
    mutationFn: (data) => policyService.createPolicy({ ...data, policyholderId: user.id }).then(res => res.data),
    onSuccess: () => {
      queryClient.invalidateQueries(['policies']);
      setIsModalOpen(false);
      setFormData({
        policyType: 'HEALTH',
        premiumAmount: '',
        startDate: '',
        endDate: '',
      });
    },
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    createPolicyMutation.mutate(formData);
  };

  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-4 sm:flex-row sm:justify-between sm:items-end">
        <div>
          <h1 className="text-3xl sm:text-4xl font-extrabold tracking-tight text-slate-900">Insurance Policies</h1>
          <p className="text-slate-600">View and manage your insurance coverage.</p>
        </div>
        {user.role === 'POLICYHOLDER' && (
          <button
            onClick={() => setIsModalOpen(true)}
            className="inline-flex items-center gap-2 rounded-2xl bg-gradient-to-r from-blue-600 via-indigo-600 to-fuchsia-600 px-4 py-3 text-white font-semibold shadow-sm hover:shadow-md transition-all"
          >
            <Plus size={20} />
            New Policy
          </button>
        )}
      </div>

      {isLoading ? (
        <div className="text-center py-12 text-slate-600">Loading policies...</div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {policies?.map(policy => (
            <div key={policy.id} className="bg-white/80 rounded-2xl border border-slate-200/70 shadow-sm hover:shadow-md transition-all overflow-hidden backdrop-blur">
              <div className="p-6">
                <div className="flex justify-between items-start mb-4">
                  <div className="p-2 bg-blue-50 rounded-2xl text-blue-600 border border-white/60">
                    <Shield size={24} />
                  </div>
                  <span className={`text-xs px-2 py-1 rounded-full ${
                    policy.status === 'ACTIVATE' ? 'bg-green-100 text-green-700' : policy.status === 'EXPIRED' ? 'bg-red-100 text-red-700' : 'bg-gray-100 text-gray-700'
                  }`}>
                    {policy.status}
                  </span>
                </div>
                <h3 className="text-xl font-extrabold text-slate-900 mb-1">{policy.policyType}</h3>
                <p className="text-sm text-slate-500 mb-4">Policy ID: {policy.id}</p>
                
                <div className="space-y-3">
                  <div className="flex items-center gap-2 text-gray-600">
                    <Calendar size={16} />
                    <span className="text-sm text-slate-600">Expires: {new Date(policy.endDate).toLocaleDateString()}</span>
                  </div>
                  {user.role !== 'POLICYHOLDER' && (
                    <div className="flex items-center gap-2 text-gray-600 border-top pt-2">
                      <User size={16} />
                      <span className="text-sm font-semibold text-slate-700">Holder ID: {policy.policyholderId}</span>
                    </div>
                  )}
                </div>
              </div>
              <div className="bg-gradient-to-r from-slate-50 to-white p-4 border-t border-slate-200/70 flex justify-between items-center">
                <span className="text-sm text-slate-600 font-semibold">Premium: ${policy.premiumAmount}/mo</span>
                <button className="text-blue-600 text-sm font-semibold hover:underline">Details</button>
              </div>
            </div>
          ))}
        </div>
      )}

      {/* New Policy Modal */}
      {isModalOpen && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center p-4 z-50">
          <div className="bg-white/90 backdrop-blur rounded-3xl shadow-2xl max-w-md w-full p-6 border border-white/40">
            <h2 className="text-2xl font-extrabold mb-6 text-slate-900">Create New Policy</h2>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Policy Type</label>
                <select
                  value={formData.policyType}
                  onChange={(e) => setFormData({ ...formData, policyType: e.target.value })}
                  className="w-full p-3 rounded-2xl border border-slate-200 bg-white focus:ring-2 focus:ring-blue-500/40 outline-none"
                >
                  <option value="HEALTH">Health</option>
                  <option value="LIFE">Life</option>
                  <option value="AUTO">Auto</option>
                  <option value="HOME">Home</option>
                </select>
              </div>
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-slate-700 mb-1">Monthly Premium</label>
                  <input
                    type="number"
                    value={formData.premiumAmount}
                    onChange={(e) => setFormData({ ...formData, premiumAmount: e.target.value })}
                    className="w-full p-3 rounded-2xl border border-slate-200 bg-white focus:ring-2 focus:ring-blue-500/40 outline-none"
                    required
                  />
                </div>
              </div>
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-slate-700 mb-1">Start Date</label>
                  <input
                    type="date"
                    value={formData.startDate}
                    onChange={(e) => setFormData({ ...formData, startDate: e.target.value })}
                    className="w-full p-3 rounded-2xl border border-slate-200 bg-white focus:ring-2 focus:ring-blue-500/40 outline-none"
                    required
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-slate-700 mb-1">End Date</label>
                  <input
                    type="date"
                    value={formData.endDate}
                    onChange={(e) => setFormData({ ...formData, endDate: e.target.value })}
                    className="w-full p-3 rounded-2xl border border-slate-200 bg-white focus:ring-2 focus:ring-blue-500/40 outline-none"
                    required
                  />
                </div>
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
                  disabled={createPolicyMutation.isPending}
                  className="flex-1 rounded-2xl bg-gradient-to-r from-blue-600 via-indigo-600 to-fuchsia-600 text-white px-4 py-3 font-semibold shadow-sm hover:shadow-md transition-all disabled:opacity-60"
                >
                  {createPolicyMutation.isPending ? 'Creating...' : 'Create Policy'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default Policies;
