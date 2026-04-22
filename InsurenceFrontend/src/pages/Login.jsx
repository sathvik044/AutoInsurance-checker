import React, { useState } from 'react';
import { useMutation } from '@tanstack/react-query';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import { useAuth } from '../context/useAuth';
import { LogIn, Shield, Lock, Mail } from 'lucide-react';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMsg, setErrorMsg] = useState('');
  const { login } = useAuth();
  const navigate = useNavigate();

  const loginMutation = useMutation({
    mutationFn: async (credentials) => {
      const response = await axios.post('http://localhost:8080/api/login', credentials);
      return response.data;
    },
    onSuccess: (data) => {
      // The backend returns a LoginResponse containing {id, name, email, token, role, message}
      login(data);
      navigate('/');
    },
    onError: (error) => {
      setErrorMsg(error.response?.data?.message || 'Invalid email or password ❌');
    }
  });

  const handleLogin = (e) => {
    e.preventDefault();
    setErrorMsg('');
    if (email.trim() && password.trim()) {
      loginMutation.mutate({ email, password });
    }
  };

  return (
    <div className="mx-auto max-w-lg py-10 sm:py-20 px-4">
      <div className="relative overflow-hidden rounded-3xl border border-white/40 bg-white/70 p-8 sm:p-10 shadow-2xl backdrop-blur-xl">
        <div className="pointer-events-none absolute inset-0 bg-gradient-to-br from-blue-600/5 via-indigo-600/5 to-fuchsia-600/5" />

        <div className="relative text-center mb-8">
          <div className="mx-auto mb-4 inline-flex h-16 w-16 items-center justify-center rounded-2xl bg-gradient-to-br from-blue-600 via-indigo-600 to-fuchsia-600 text-white shadow-lg">
            <Shield size={34} />
          </div>
          <h1 className="text-3xl font-extrabold tracking-tight text-slate-900">Welcome Back</h1>
          <p className="text-slate-500 mt-2">Sign in to your account</p>
        </div>

        {errorMsg && (
          <div className="mb-6 p-4 rounded-xl bg-red-50 text-red-600 text-sm font-medium border border-red-100 flex items-center justify-center">
            {errorMsg}
          </div>
        )}

        <form onSubmit={handleLogin} className="relative space-y-5">
          <div className="relative">
            <label className="text-sm font-semibold text-slate-700 ml-1 mb-1 block">Email</label>
            <div className="relative">
              <Mail className="absolute left-3.5 top-3.5 text-slate-400" size={20} />
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="w-full pl-11 p-3.5 rounded-2xl border border-slate-200 bg-white/80 focus:ring-4 focus:ring-blue-500/20 focus:border-blue-500 outline-none transition-all"
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
                className="w-full pl-11 p-3.5 rounded-2xl border border-slate-200 bg-white/80 focus:ring-4 focus:ring-blue-500/20 focus:border-blue-500 outline-none transition-all"
                placeholder="••••••••"
                required
              />
            </div>
          </div>

          <button
            type="submit"
            disabled={loginMutation.isPending}
            className="w-full mt-4 rounded-2xl bg-gradient-to-r from-blue-600 via-indigo-600 to-fuchsia-600 text-white py-4 font-semibold text-lg shadow-md hover:shadow-xl hover:scale-[1.02] transition-all flex justify-center items-center gap-2 disabled:opacity-60 disabled:hover:scale-100"
          >
            {loginMutation.isPending ? 'Authenticating...' : (
              <>
                <LogIn size={20} />
                Sign In
              </>
            )}
          </button>
        </form>

        <p className="mt-8 text-center text-slate-600 text-sm">
          Don't have an account?{' '}
          <Link to="/register" className="font-semibold text-blue-600 hover:text-blue-500 hover:underline transition-colors">
            Create an account
          </Link>
        </p>
      </div>
    </div>
  );
};

export default Login;
