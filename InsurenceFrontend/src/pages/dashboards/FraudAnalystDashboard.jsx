import React from 'react';
import { useAuth } from '../../context/useAuth';
import { ShieldAlert, Fingerprint, Activity, AlertOctagon } from 'lucide-react';

const FraudAnalystDashboard = () => {
  const { user } = useAuth();

  return (
    <div className="space-y-6">
      <div className="bg-gradient-to-r from-red-600 to-orange-600 rounded-3xl p-8 sm:p-10 text-white shadow-xl flex items-center justify-between">
        <div>
          <h1 className="text-3xl sm:text-4xl font-bold mb-2">Fraud Analysis Control</h1>
          <p className="text-red-100 text-lg">Investigate suspicious patterns and flag high-risk claims.</p>
        </div>
        <div className="hidden sm:flex h-20 w-20 bg-white/20 rounded-full items-center justify-center backdrop-blur">
          <ShieldAlert size={40} className="text-white" />
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-white rounded-3xl p-6 border border-slate-100 shadow-sm hover:shadow-md transition-all">
          <div className="h-12 w-12 rounded-2xl bg-red-50 text-red-600 flex items-center justify-center mb-4">
            <AlertOctagon size={24} />
          </div>
          <h2 className="text-xl font-bold text-slate-800">High Risk Claims</h2>
          <p className="text-3xl font-black text-slate-900 mt-2">7</p>
          <div className="mt-4 text-sm font-medium text-red-600 cursor-pointer hover:underline">Investigate &rarr;</div>
        </div>

        <div className="bg-white rounded-3xl p-6 border border-slate-100 shadow-sm hover:shadow-md transition-all">
          <div className="h-12 w-12 rounded-2xl bg-orange-50 text-orange-500 flex items-center justify-center mb-4">
            <Fingerprint size={24} />
          </div>
          <h2 className="text-xl font-bold text-slate-800">Identity Checks</h2>
          <p className="text-3xl font-black text-slate-900 mt-2">14</p>
          <div className="mt-4 text-sm font-medium text-orange-500 cursor-pointer hover:underline">Run verification &rarr;</div>
        </div>

        <div className="bg-white rounded-3xl p-6 border border-slate-100 shadow-sm md:col-span-1 sm:col-span-2">
           <div className="flex items-center gap-2 mb-4">
               <Activity className="text-slate-400" size={20} />
               <h3 className="font-bold text-lg text-slate-800">Network Alerts</h3>
           </div>
           <div className="bg-red-50 text-red-700 text-sm p-3 rounded-xl border border-red-100 mb-2 font-medium">
             Pattern match identified involving user <strong>US-992</strong>.
           </div>
           <div className="bg-orange-50 text-orange-700 text-sm p-3 rounded-xl border border-orange-100 font-medium">
             Multiple claims from same IP on recent policy.
           </div>
        </div>
      </div>
    </div>
  );
};

export default FraudAnalystDashboard;
