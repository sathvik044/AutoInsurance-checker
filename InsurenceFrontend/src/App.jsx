import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { AuthProvider } from './context/AuthContext';
import { useAuth } from './context/useAuth';
import Navbar from './components/Navbar';
import RoleRoute from './components/RoleRoute';
import Login from './pages/Login';
import Register from './pages/Register';
import PolicyHolderDashboard from './pages/dashboards/PolicyHolderDashboard';
import ClaimAdjusterDashboard from './pages/dashboards/ClaimAdjusterDashboard';
import ManagerDashboard from './pages/dashboards/ManagerDashboard';
import FraudAnalystDashboard from './pages/dashboards/FraudAnalystDashboard';
import './App.css';

const queryClient = new QueryClient();

// Redirect user to their specific dashboard upon root path based on role
const RootRedirect = () => {
  const { user } = useAuth();
  if (!user) return <Navigate to="/login" replace />;
  const role = user.role?.toUpperCase() || '';
  if (role.includes('POLICY')) return <Navigate to="/dashboard/policyholder" replace />;
  if (role.includes('CLAIM')) return <Navigate to="/dashboard/adjuster" replace />;
  if (role.includes('MANAGER')) return <Navigate to="/dashboard/manager" replace />;
  if (role.includes('FRAUD')) return <Navigate to="/dashboard/fraud" replace />;
  return <Navigate to="/login" replace />;
};

function AppContent() {
  const { user } = useAuth();

  return (
    <div className="min-h-screen bg-slate-50 text-slate-900 font-sans">
      {user && <Navbar />}
      <main className="mx-auto w-full max-w-7xl px-4 sm:px-6 lg:px-8 py-6 sm:py-8">
        <Routes>
          <Route path="/login" element={!user ? <Login /> : <Navigate to="/" replace />} />
          <Route path="/register" element={!user ? <Register /> : <Navigate to="/" replace />} />
          
          <Route path="/" element={<RootRedirect />} />
          
          <Route path="/dashboard/policyholder" element={
            <RoleRoute allowedRoles={['POLICYHOLDER', 'USER']}>
              <PolicyHolderDashboard />
            </RoleRoute>
          } />
          
          <Route path="/dashboard/adjuster" element={
            <RoleRoute allowedRoles={['CLAIMADJUSTER', 'ADMIN']}>
              <ClaimAdjusterDashboard />
            </RoleRoute>
          } />
          
          <Route path="/dashboard/manager" element={
            <RoleRoute allowedRoles={['MANAGER', 'ADMIN']}>
              <ManagerDashboard />
            </RoleRoute>
          } />

          <Route path="/dashboard/fraud" element={
            <RoleRoute allowedRoles={['FRAUDANALYST', 'ADMIN']}>
              <FraudAnalystDashboard />
            </RoleRoute>
          } />

          <Route path="/unauthorized" element={
            <div className="text-center py-20">
              <h1 className="text-4xl font-bold text-red-500">403</h1>
              <p className="text-xl text-slate-700 mt-4">You are not authorized to view this page.</p>
            </div>
          } />
        </Routes>
      </main>
    </div>
  );
}

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <AuthProvider>
        <Router>
          <AppContent />
        </Router>
      </AuthProvider>
    </QueryClientProvider>
  );
}

export default App;
