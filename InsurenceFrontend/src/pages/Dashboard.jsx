import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { useAuth } from '../context/useAuth';
import { policyService, claimService } from '../services/api';
import { FileText, ClipboardList, AlertCircle, CheckCircle } from 'lucide-react';

const Dashboard = () => {
  const { user } = useAuth();

  const { data: policies } = useQuery({
    queryKey: ['policies', user.id],
    queryFn: () => (user.role === 'POLICYHOLDER' 
      ? policyService.getPoliciesByUserId(user.id) 
      : policyService.getPolicies()).then(res => res.data),
  });

  const { data: claims } = useQuery({
    queryKey: ['claims', user.id],
    queryFn: () => (user.role === 'POLICYHOLDER' 
      ? claimService.getClaimsByUserId(user.id) 
      : claimService.getAllClaims()).then(res => res.data),
  });

  const stats = [
    {
      label: 'Active Policies',
      value: policies?.filter(p => p.status === 'ACTIVATE').length || 0,
      icon: <FileText className="text-blue-600" />,
      color: 'bg-blue-50'
    },
    {
      label: 'Pending Claims',
      value: claims?.filter(c => c.status === 'CREATED' || c.status === 'SUBMITTED').length || 0,
      icon: <AlertCircle className="text-yellow-600" />,
      color: 'bg-yellow-50'
    },
    {
      label: 'Approved Claims',
      value: claims?.filter(c => c.status === 'APPROVED' || c.status === 'SETTLED').length || 0,
      icon: <CheckCircle className="text-green-600" />,
      color: 'bg-green-50'
    },
    {
      label: 'Total Claims',
      value: claims?.length || 0,
      icon: <ClipboardList className="text-purple-600" />,
      color: 'bg-purple-50'
    }
  ];

  return (
    <div className="space-y-8">
      <div>
        <div className="flex flex-col gap-2 sm:flex-row sm:items-end sm:justify-between">
          <div>
            <h1 className="text-3xl sm:text-4xl font-extrabold tracking-tight text-slate-900">Welcome back, {user.name}!</h1>
            <p className="text-slate-600">Here's what's happening with your insurance account.</p>
          </div>
          <div className="inline-flex items-center gap-2 rounded-2xl border border-white/40 bg-white/70 px-4 py-2 text-sm font-semibold text-slate-700 shadow-sm backdrop-blur">
            <span className="h-2 w-2 rounded-full bg-emerald-500" />
            <span className="capitalize">{user.role?.toLowerCase()}</span>
          </div>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {stats.map((stat, index) => (
          <div key={index} className="bg-white/80 p-6 rounded-2xl border border-slate-200/70 shadow-sm backdrop-blur hover:shadow-md transition-shadow">
            <div className="flex items-center justify-between mb-4">
              <div className={`p-3 rounded-2xl ${stat.color} border border-white/60`}>
                {stat.icon}
              </div>
              <span className="text-3xl font-extrabold tracking-tight text-slate-900">{stat.value}</span>
            </div>
            <p className="text-slate-600 font-semibold">{stat.label}</p>
          </div>
        ))}
      </div>

      <div className="grid lg:grid-cols-2 gap-8">
        {/* Recent Policies */}
        <div className="bg-white/80 rounded-2xl border border-slate-200/70 shadow-sm overflow-hidden backdrop-blur">
          <div className="p-6 border-b border-slate-200/70 flex justify-between items-center">
            <h2 className="font-extrabold text-lg sm:text-xl text-slate-900">Recent Policies</h2>
            <button className="text-blue-600 text-sm font-semibold hover:underline">View all</button>
          </div>
          <div className="divide-y">
            {policies?.slice(0, 5).map(policy => (
              <div key={policy.id} className="p-4 flex justify-between items-center">
                <div>
                  <p className="font-semibold text-slate-900">{policy.policyType}</p>
                  <p className="text-sm text-slate-500">ID: {policy.id}</p>
                </div>
                <div className="text-right">
                  <p className="font-semibold text-slate-900">${policy.premiumAmount}/mo</p>
                  <span className={`text-xs px-2 py-1 rounded-full ${
                    policy.status === 'ACTIVATE' ? 'bg-green-100 text-green-700' : 'bg-gray-100 text-gray-700'
                  }`}>
                    {policy.status}
                  </span>
                </div>
              </div>
            ))}
            {(!policies || policies.length === 0) && (
              <div className="p-10 text-center text-slate-500">No policies found</div>
            )}
          </div>
        </div>

        {/* Recent Claims */}
        <div className="bg-white/80 rounded-2xl border border-slate-200/70 shadow-sm overflow-hidden backdrop-blur">
          <div className="p-6 border-b border-slate-200/70 flex justify-between items-center">
            <h2 className="font-extrabold text-lg sm:text-xl text-slate-900">Recent Claims</h2>
            <button className="text-blue-600 text-sm font-semibold hover:underline">View all</button>
          </div>
          <div className="divide-y">
            {claims?.slice(0, 5).map(claim => (
              <div key={claim.id} className="p-4 flex justify-between items-center">
                <div>
                  <p className="font-semibold text-slate-900">Claim #{claim.id}</p>
                  <p className="text-sm text-slate-500">{claim.claimType}</p>
                </div>
                <div className="text-right">
                  <span className={`text-xs px-2 py-1 rounded-full ${
                    claim.status === 'APPROVED' ? 'bg-green-100 text-green-700' : 
                    (claim.status === 'CREATED' || claim.status === 'SUBMITTED') ? 'bg-yellow-100 text-yellow-700' : 
                    'bg-gray-100 text-gray-700'
                  }`}>
                    {claim.status}
                  </span>
                </div>
              </div>
            ))}
            {(!claims || claims.length === 0) && (
              <div className="p-10 text-center text-slate-500">No claims found</div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
