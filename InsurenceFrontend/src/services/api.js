import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use(
  (config) => {
    const savedUser = localStorage.getItem('user');
    if (savedUser) {
      const { token } = JSON.parse(savedUser);
      if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
      }
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export const userService = {
  getUsers: (role) => api.get(`/users${role ? `?role=${role}` : ''}`),
  createUser: (user) => api.post('/users', user),
};

export const policyService = {
  getPolicies: (userId) => api.get(`/policies${userId ? `?userId=${userId}` : ''}`),
  getPolicyById: (id) => api.get(`/policies/${id}`),
  createPolicy: (policy) => api.post('/policies', policy),
  getPoliciesByUserId: (userId) => api.get(`/policies/user/${userId}`),
};

export const claimService = {
  createClaim: (claim) => api.post('/claims', claim),
  getAllClaims: () => api.get('/claims'),
  getClaimById: (id) => api.get(`/claims/${id}`),
  getClaimsByUserId: (userId) => api.get(`/claims/user/${userId}`),
  submitClaim: (id) => api.put(`/claims/${id}/submit`),
  verifyDocuments: (id) => api.put(`/claims/${id}/verify-documents`),
  moveToReview: (id) => api.put(`/claims/${id}/review`),
  autoValidate: (id) => api.put(`/claims/${id}/auto-validate`),
  adjusterApprove: (id) => api.put(`/claims/${id}/adjuster-approve`),
  managerApprove: (id) => api.put(`/claims/${id}/manager-approve`),
  approveClaim: (id) => api.put(`/claims/${id}/approve`),
  settleClaim: (id) => api.put(`/claims/${id}/settle`),
  rejectClaim: (id) => api.put(`/claims/${id}/reject`),
  uploadDocuments: (id, docs) => api.post(`/claims/${id}/documents`, docs),
};

export const documentService = {
  uploadDocumentsToClaim: (claimId, docs) => api.post(`/documents/claim/${claimId}`, docs),
  getDocumentsByClaimId: (claimId) => api.get(`/documents/claim/${claimId}`),
  getDocumentsByUserId: (userId) => api.get(`/documents/user/${userId}`),
  verifyDocument: (documentId, status) => api.put(`/documents/${documentId}/verify?status=${status}`),
  deleteDocument: (documentId) => api.delete(`/documents/${documentId}`),
};

export default api;
