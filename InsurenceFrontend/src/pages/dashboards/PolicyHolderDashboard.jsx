import React from 'react';
import { useAuth } from '../../context/useAuth';
import { Shield, FileText, Clock, CheckCircle } from 'lucide-react';

const PolicyHolderDashboard = () => {
  const { user } = useAuth();

  return (
    <div className="space-y-6">
      <div className="bg-gradient-to-r from-blue-600 to-indigo-700 rounded-3xl p-8 sm:p-10 text-white shadow-xl">
        <h1 className="text-3xl sm:text-4xl font-bold mb-2">Welcome back, {user?.name}</h1>
        <p className="text-blue-100 text-lg">Manage your active policies and track claims.</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-white rounded-3xl p-6 border border-slate-100 shadow-sm hover:shadow-md transition-all">
          <div className="h-12 w-12 rounded-2xl bg-blue-50 text-blue-600 flex items-center justify-center mb-4">
            <Shield size={24} />
          </div>
          <h2 className="text-xl font-bold text-slate-800">Active Policies</h2>
          <p className="text-3xl font-black text-slate-900 mt-2">2</p>
          <div className="mt-4 text-sm font-medium text-blue-600 cursor-pointer hover:underline">View policies &rarr;</div>
        </div>

        <div className="bg-white rounded-3xl p-6 border border-slate-100 shadow-sm hover:shadow-md transition-all">
          <div className="h-12 w-12 rounded-2xl bg-amber-50 text-amber-500 flex items-center justify-center mb-4">
            <Clock size={24} />
          </div>
          <h2 className="text-xl font-bold text-slate-800">Pending Claims</h2>
          <p className="text-3xl font-black text-slate-900 mt-2">1</p>
          <div className="mt-4 text-sm font-medium text-amber-500 cursor-pointer hover:underline">Track status &rarr;</div>
        </div>

        <div className="bg-white rounded-3xl p-6 border border-slate-100 shadow-sm hover:shadow-md transition-all md:col-span-1 sm:col-span-2">
          <div className="h-12 w-12 rounded-2xl bg-green-50 text-green-500 flex items-center justify-center mb-4">
            <CheckCircle size={24} />
          </div>
          <h2 className="text-xl font-bold text-slate-800">Approved Claims</h2>
          <p className="text-3xl font-black text-slate-900 mt-2">0</p>
          <div className="mt-4 text-sm font-medium text-green-500 cursor-pointer hover:underline">View history &rarr;</div>
        </div>
      </div>
    </div>
  );
};

export default PolicyHolderDashboard;
