import React from 'react';
import { useAuth } from '../../context/useAuth';
import { Briefcase, BarChart3, TrendingUp, AlertTriangle } from 'lucide-react';

const ManagerDashboard = () => {
  const { user } = useAuth();

  return (
    <div className="space-y-6">
      <div className="bg-gradient-to-r from-fuchsia-600 to-purple-700 rounded-3xl p-8 sm:p-10 text-white shadow-xl">
        <h1 className="text-3xl sm:text-4xl font-bold mb-2">Manager Oversight</h1>
        <p className="text-fuchsia-100 text-lg">Oversee claims, manage risk, and approve high-value settlements.</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
        <div className="bg-white rounded-3xl p-6 border border-slate-100 shadow-sm col-span-1 md:col-span-2 hover:shadow-md transition-all">
          <div className="flex items-center gap-4 mb-4">
            <div className="h-12 w-12 rounded-2xl bg-purple-50 text-purple-600 flex items-center justify-center">
              <Briefcase size={24} />
            </div>
            <div>
              <h2 className="text-xl font-bold text-slate-800">Pending Final Approval</h2>
              <p className="text-sm text-slate-500">Claims waiting for your signature</p>
            </div>
          </div>
          <p className="text-4xl font-black text-slate-900 mt-4">8</p>
          <div className="mt-4 text-sm font-medium text-purple-600 cursor-pointer hover:underline">Review queue &rarr;</div>
        </div>

        <div className="bg-white rounded-3xl p-6 border border-slate-100 shadow-sm col-span-1 md:col-span-2 hover:shadow-md transition-all">
          <div className="flex items-center gap-4 mb-4">
            <div className="h-12 w-12 rounded-2xl bg-rose-50 text-rose-600 flex items-center justify-center">
              <AlertTriangle size={24} />
            </div>
            <div>
              <h2 className="text-xl font-bold text-slate-800">Escalated Disputes</h2>
              <p className="text-sm text-slate-500">Requires immediate attention</p>
            </div>
          </div>
          <p className="text-4xl font-black text-slate-900 mt-4">3</p>
          <div className="mt-4 text-sm font-medium text-rose-600 cursor-pointer hover:underline">Resolve disputes &rarr;</div>
        </div>
      </div>
      
      <div className="bg-white rounded-3xl p-6 border border-slate-100 shadow-sm mt-6">
           <div className="flex items-center justify-between mb-6">
             <h3 className="font-bold text-xl text-slate-800 flex items-center gap-2"><BarChart3 className="text-indigo-500"/> Performance Metrics</h3>
             <button className="text-sm border border-slate-200 rounded-lg px-3 py-1 text-slate-600 hover:bg-slate-50">This Month</button>
           </div>
           
           <div className="h-40 w-full bg-slate-50 rounded-xl flex items-center justify-center border border-slate-100 border-dashed">
              <span className="text-slate-400 font-medium">Chart visualization placeholder (Revenue / Paid Claims)</span>
           </div>
      </div>
    </div>
  );
};

export default ManagerDashboard;
