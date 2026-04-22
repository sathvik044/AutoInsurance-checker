import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/useAuth';
import { Shield, LayoutDashboard, FileText, ClipboardList, LogOut, Menu, X } from 'lucide-react';

const Navbar = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const [mobileOpen, setMobileOpen] = useState(false);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="sticky top-0 z-50 border-b border-white/30 bg-white/70 backdrop-blur-xl">
      <div className="mx-auto w-full max-w-7xl px-4 sm:px-6 lg:px-8">
        <div className="flex h-16 items-center justify-between">
          <div className="flex items-center gap-3 sm:gap-6">
            <Link to="/" className="flex items-center gap-3">
              <span className="inline-flex h-10 w-10 items-center justify-center rounded-xl bg-gradient-to-br from-blue-600 via-indigo-600 to-fuchsia-600 text-white shadow-sm">
                <Shield size={22} />
              </span>
              <div className="leading-tight">
                <div className="text-base sm:text-lg font-extrabold tracking-tight text-slate-900">InsureHCL</div>
                <div className="hidden sm:block text-xs text-slate-500">Policies & Claims</div>
              </div>
            </Link>

            <div className="hidden md:flex items-center gap-2">
              <Link
                to="/"
                className="inline-flex items-center gap-2 rounded-xl px-3 py-2 text-sm font-semibold text-slate-700 hover:bg-slate-900/5 hover:text-slate-900 transition-colors"
              >
                <LayoutDashboard size={18} />
                <span>Dashboard</span>
              </Link>
              <Link
                to="/policies"
                className="inline-flex items-center gap-2 rounded-xl px-3 py-2 text-sm font-semibold text-slate-700 hover:bg-slate-900/5 hover:text-slate-900 transition-colors"
              >
                <FileText size={18} />
                <span>Policies</span>
              </Link>
              <Link
                to="/claims"
                className="inline-flex items-center gap-2 rounded-xl px-3 py-2 text-sm font-semibold text-slate-700 hover:bg-slate-900/5 hover:text-slate-900 transition-colors"
              >
                <ClipboardList size={18} />
                <span>Claims</span>
              </Link>
            </div>
          </div>

          <div className="flex items-center gap-2 sm:gap-3">
            <div className="hidden sm:block text-right">
              <p className="text-sm font-semibold text-slate-900">{user.name}</p>
              <p className="text-xs text-slate-500 capitalize">{user.role}</p>
            </div>

            <button
              onClick={handleLogout}
              className="inline-flex items-center justify-center rounded-xl p-2 text-slate-600 hover:text-red-600 hover:bg-red-50 transition-colors"
              title="Logout"
            >
              <LogOut size={20} />
            </button>

            <button
              onClick={() => setMobileOpen(v => !v)}
              className="md:hidden inline-flex items-center justify-center rounded-xl p-2 text-slate-700 hover:bg-slate-900/5 transition-colors"
              title="Menu"
              aria-label="Open menu"
            >
              {mobileOpen ? <X size={22} /> : <Menu size={22} />}
            </button>
          </div>
        </div>

        {mobileOpen && (
          <div className="md:hidden pb-4">
            <div className="mt-2 grid gap-2 rounded-2xl border border-slate-200/70 bg-white/70 p-2 backdrop-blur">
              <Link
                to="/"
                onClick={() => setMobileOpen(false)}
                className="inline-flex items-center gap-2 rounded-xl px-3 py-2 text-sm font-semibold text-slate-700 hover:bg-slate-900/5"
              >
                <LayoutDashboard size={18} />
                <span>Dashboard</span>
              </Link>
              <Link
                to="/policies"
                onClick={() => setMobileOpen(false)}
                className="inline-flex items-center gap-2 rounded-xl px-3 py-2 text-sm font-semibold text-slate-700 hover:bg-slate-900/5"
              >
                <FileText size={18} />
                <span>Policies</span>
              </Link>
              <Link
                to="/claims"
                onClick={() => setMobileOpen(false)}
                className="inline-flex items-center gap-2 rounded-xl px-3 py-2 text-sm font-semibold text-slate-700 hover:bg-slate-900/5"
              >
                <ClipboardList size={18} />
                <span>Claims</span>
              </Link>
            </div>

            <div className="mt-3 rounded-2xl border border-slate-200/70 bg-white/70 p-3 backdrop-blur sm:hidden">
              <p className="text-sm font-semibold text-slate-900">{user.name}</p>
              <p className="text-xs text-slate-500 capitalize">{user.role}</p>
            </div>
          </div>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
