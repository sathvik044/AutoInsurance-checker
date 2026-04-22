import React, { useState } from 'react';
import { useMutation } from '@tanstack/react-query';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import { UserPlus, Shield, Lock, Mail, User, Briefcase } from 'lucide-react';

const Register = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('POLICYHOLDER');
  const [errorMsg, setErrorMsg] = useState('');
  const navigate = useNavigate();

  const registerMutation = useMutation({
    mutationFn: async (userData) => {
      const response = await axios.post('http://localhost:8080/api/register', userData);
      return response.data;
    },
    onSuccess: () => {
      navigate('/login');
    },
    onError: (error) => {
      setErrorMsg(error.response?.data || 'Failed to create account ❌');
    }
  });

  const handleRegister = (e) => {
    e.preventDefault();
    setErrorMsg('');
    if (name.trim() && email.trim() && password.trim()) {
      registerMutation.mutate({ name, email, password, role });
    }
  };

  return (
    <div className="mx-auto max-w-lg py-10 sm:py-20 px-4">
      <div className="relative overflow-hidden rounded-3xl border border-white/40 bg-white/70 p-8 sm:p-10 shadow-2xl backdrop-blur-xl">
        <div className="pointer-events-none absolute inset-0 bg-gradient-to-br from-green-600/5 via-emerald-600/5 to-teal-600/5" />

        <div className="relative text-center mb-8">
          <div className="mx-auto mb-4 inline-flex h-16 w-16 items-center justify-center rounded-2xl bg-gradient-to-br from-green-500 via-emerald-600 to-teal-600 text-white shadow-lg">
            <UserPlus size={34} />
          </div>
          <h1 className="text-3xl font-extrabold tracking-tight text-slate-900">Create Account</h1>
          <p className="text-slate-500 mt-2">Join InsureHCL today</p>
        </div>

        {errorMsg && (
          <div className="mb-6 p-4 rounded-xl bg-red-50 text-red-600 text-sm font-medium border border-red-100 flex items-center justify-center">
            {typeof errorMsg === 'string' ? errorMsg : 'Registration failed'}
          </div>
        )}

        <form onSubmit={handleRegister} className="relative space-y-5">
          <div className="relative">
            <label className="text-sm font-semibold text-slate-700 ml-1 mb-1 block">Full Name</label>
            <div className="relative">
              <User className="absolute left-3.5 top-3.5 text-slate-400" size={20} />
              <input
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                className="w-full pl-11 p-3.5 rounded-2xl border border-slate-200 bg-white/80 focus:ring-4 focus:ring-green-500/20 focus:border-green-500 outline-none transition-all"
                placeholder="John Doe"
                required
              />
            </div>
          </div>

          <div className="relative">
            <label className="text-sm font-semibold text-slate-700 ml-1 mb-1 block">Email</label>
            <div className="relative">
              <Mail className="absolute left-3.5 top-3.5 text-slate-400" size={20} />
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="w-full pl-11 p-3.5 rounded-2xl border border-slate-200 bg-white/80 focus:ring-4 focus:ring-green-500/20 focus:border-green-500 outline-none transition-all"
                placeholder="name@example.com"
                required
              />
            </div>
          </div>
          
          <div className="relative">
            <label className="text-sm font-semibold text-slate-700 ml-1 mb-1 block">Password</label>
            <div className="relative">
              <Lock className="absolute left-3.5 top-3.5 text-slate-400" size={20} />
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="w-full pl-11 p-3.5 rounded-2xl border border-slate-200 bg-white/80 focus:ring-4 focus:ring-green-500/20 focus:border-green-500 outline-none transition-all"
                placeholder="••••••••"
                required
              />
            </div>
          </div>

          <div className="relative">
            <label className="text-sm font-semibold text-slate-700 ml-1 mb-1 block">Account Type / Role</label>
            <div className="relative">
              <Briefcase className="absolute left-3.5 top-3.5 text-slate-400" size={20} />
              <select
                value={role}
                onChange={(e) => setRole(e.target.value)}
                className="w-full pl-11 p-3.5 rounded-2xl border border-slate-200 bg-white/80 focus:ring-4 focus:ring-green-500/20 focus:border-green-500 outline-none transition-all appearance-none"
                required
              >
                <option value="POLICYHOLDER">Policy Holder</option>
                <option value="CLAIMADJUSTER">Claim Adjuster</option>
                <option value="FRAUDANALYST">Fraud Analyst</option>
                <option value="MANAGER">Manager</option>
              </select>
            </div>
          </div>

          <button
            type="submit"
            disabled={registerMutation.isPending}
            className="w-full mt-4 rounded-2xl bg-gradient-to-r from-green-500 via-emerald-600 to-teal-600 text-white py-4 font-semibold text-lg shadow-md hover:shadow-xl hover:scale-[1.02] transition-all flex justify-center items-center gap-2 disabled:opacity-60 disabled:hover:scale-100"
          >
            {registerMutation.isPending ? 'Creating Account...' : (
              <>
                <UserPlus size={20} />
                Register
              </>
            )}
          </button>
        </form>

        <p className="mt-8 text-center text-slate-600 text-sm">
          Already have an account?{' '}
          <Link to="/login" className="font-semibold text-green-600 hover:text-green-500 hover:underline transition-colors">
            Sign In
          </Link>
        </p>
      </div>
    </div>
  );
};

export default Register;
