import React from 'react';
import { useAuth } from '../../context/useAuth';
import { FileSearch, CheckSquare, XSquare } from 'lucide-react';

const ClaimAdjusterDashboard = () => {
  const { user } = useAuth();

  return (
    <div className="space-y-6">
      <div className="bg-gradient-to-r from-teal-600 to-emerald-700 rounded-3xl p-8 sm:p-10 text-white shadow-xl">
        <h1 className="text-3xl sm:text-4xl font-bold mb-2">Adjuster Portal</h1>
        <p className="text-teal-100 text-lg">Review claims thoroughly, verify documents, and enforce policies.</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-white rounded-3xl p-6 border border-slate-100 shadow-sm hover:shadow-md transition-all">
          <div className="h-12 w-12 rounded-2xl bg-teal-50 text-teal-600 flex items-center justify-center mb-4">
            <FileSearch size={24} />
          </div>
          <h2 className="text-xl font-bold text-slate-800">Pending Review</h2>
          <p className="text-3xl font-black text-slate-900 mt-2">12</p>
          <div className="mt-4 text-sm font-medium text-teal-600 cursor-pointer hover:underline">Start reviewing &rarr;</div>
        </div>

        <div className="bg-white rounded-3xl p-6 border border-slate-100 shadow-sm hover:shadow-md transition-all">
          <div className="h-12 w-12 rounded-2xl bg-amber-50 text-amber-500 flex items-center justify-center mb-4">
            <CheckSquare size={24} />
          </div>
          <h2 className="text-xl font-bold text-slate-800">Documents to Verify</h2>
          <p className="text-3xl font-black text-slate-900 mt-2">5</p>
          <div className="mt-4 text-sm font-medium text-amber-500 cursor-pointer hover:underline">Verify now &rarr;</div>
        </div>
        
        <div className="bg-white rounded-3xl p-6 border border-slate-100 shadow-sm">
           <h3 className="font-bold text-lg mb-2 text-slate-800">Recent Activity</h3>
           <ul className="space-y-3 mt-4">
              <li className="text-sm border-b border-slate-100 pb-2"><span className="font-semibold">CLM-9921</span> approved by you</li>
              <li className="text-sm border-b border-slate-100 pb-2"><span className="font-semibold">CLM-9918</span> sent for manager review</li>
              <li className="text-sm text-slate-500 italic">No more recent activities.</li>
           </ul>
        </div>
      </div>
    </div>
  );
};

export default ClaimAdjusterDashboard;
