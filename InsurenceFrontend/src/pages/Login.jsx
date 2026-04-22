import React, { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { userService } from '../services/api';
import { useAuth } from '../context/useAuth';
import { UserPlus, LogIn, Shield } from 'lucide-react';

const Login = () => {
  const [newName, setNewName] = useState('');
  const [newEmail, setNewEmail] = useState('');
  const [newRole, setNewRole] = useState('POLICYHOLDER');
  const { login } = useAuth();
  const queryClient = useQueryClient();

  const { data: users, isLoading } = useQuery({
    queryKey: ['users'],
    queryFn: () => userService.getUsers().then(res => res.data),
  });

  const createUserMutation = useMutation({
    mutationFn: (userData) => userService.createUser(userData).then(res => res.data),
    onSuccess: (newUser) => {
      queryClient.invalidateQueries(['users']);
      login(newUser);
    },
  });

  const handleCreateUser = (e) => {
    e.preventDefault();
    if (newName.trim() && newEmail.trim()) {
      createUserMutation.mutate({ name: newName, email: newEmail, role: newRole });
    }
  };

  return (
    <div className="mx-auto max-w-5xl py-10 sm:py-14 px-4">
      <div className="relative overflow-hidden rounded-3xl border border-white/40 bg-white/70 p-8 sm:p-10 shadow-xl backdrop-blur">
        <div className="pointer-events-none absolute inset-0 bg-gradient-to-br from-blue-600/10 via-indigo-600/10 to-fuchsia-600/10" />

        <div className="relative text-center mb-10">
          <div className="mx-auto mb-4 inline-flex h-16 w-16 items-center justify-center rounded-2xl bg-gradient-to-br from-blue-600 via-indigo-600 to-fuchsia-600 text-white shadow">
            <Shield size={34} />
          </div>
          <h1 className="text-3xl sm:text-4xl font-extrabold tracking-tight text-slate-900">Welcome to InsureHCL</h1>
          <p className="text-slate-600 mt-2">Manage policies and claims with a clean workflow.</p>
        </div>

        <div className="relative grid lg:grid-cols-2 gap-6 sm:gap-8">
        {/* Existing Users */}
        <div className="bg-white/80 p-6 rounded-2xl shadow-sm border border-slate-200/70">
          <h2 className="text-lg sm:text-xl font-semibold mb-5 flex items-center gap-2 text-slate-900">
            <LogIn className="text-blue-600" />
            Select Existing User
          </h2>
          {isLoading ? (
            <div className="text-center py-8 text-slate-600">Loading users...</div>
          ) : (
            <div className="space-y-3 max-h-[420px] overflow-auto pr-1">
              {users?.map(user => (
                <button
                  key={user.id}
                  onClick={() => login(user)}
                  className="w-full text-left p-4 rounded-2xl border border-slate-200 hover:border-blue-500/60 hover:bg-blue-50/40 transition-all flex justify-between items-center group"
                >
                  <div>
                    <div className="font-semibold text-slate-900">{user.name}</div>
                    <div className="text-sm text-slate-500 capitalize">{user.role.toLowerCase()}</div>
                  </div>
                  <LogIn className="text-gray-300 group-hover:text-blue-500" size={18} />
                </button>
              ))}
              {users?.length === 0 && (
                <p className="text-center text-slate-500 py-8">No users found. Create one to get started!</p>
              )}
            </div>
          )}
        </div>

        {/* Create New User */}
        <div className="bg-white/80 p-6 rounded-2xl shadow-sm border border-slate-200/70">
          <h2 className="text-lg sm:text-xl font-semibold mb-5 flex items-center gap-2 text-slate-900">
            <UserPlus className="text-green-600" />
            Create New Profile
          </h2>
          <form onSubmit={handleCreateUser} className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1">Name</label>
              <input
                type="text"
                value={newName}
                onChange={(e) => setNewName(e.target.value)}
                className="w-full p-3 rounded-2xl border border-slate-200 bg-white focus:ring-2 focus:ring-blue-500/40 focus:border-blue-500 outline-none"
                placeholder="Enter name"
                required
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1">Email</label>
              <input
                type="email"
                value={newEmail}
                onChange={(e) => setNewEmail(e.target.value)}
                className="w-full p-3 rounded-2xl border border-slate-200 bg-white focus:ring-2 focus:ring-blue-500/40 focus:border-blue-500 outline-none"
                placeholder="Enter email"
                required
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1">Role</label>
              <select
                value={newRole}
                onChange={(e) => setNewRole(e.target.value)}
                className="w-full p-3 rounded-2xl border border-slate-200 bg-white focus:ring-2 focus:ring-blue-500/40 focus:border-blue-500 outline-none"
              >
                <option value="POLICYHOLDER">Policy Holder</option>
                <option value="CLAIM_ADJUSTER">Claim Adjuster</option>
                <option value="CLAIM_MANAGER">Claim Manager</option>
                <option value="ADMIN">Admin</option>
              </select>
            </div>
            <button
              type="submit"
              disabled={createUserMutation.isPending}
              className="w-full rounded-2xl bg-gradient-to-r from-blue-600 via-indigo-600 to-fuchsia-600 text-white py-3 font-semibold shadow-sm hover:shadow-md transition-all flex justify-center items-center gap-2 disabled:opacity-60"
            >
              {createUserMutation.isPending ? 'Creating...' : (
                <>
                  <UserPlus size={18} />
                  Create and Login
                </>
              )}
            </button>
          </form>
        </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
