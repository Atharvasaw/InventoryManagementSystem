import React from "react";
import { useNavigate } from "react-router-dom";
import ApiService from "./ApiService";
import { useEffect } from "react";

// export const ProtectedRoute = ({element: Component}) => {
//     const location = useLocation();
//     return ApiService.isAuthenticated() ? (
//         Component
//     ):(
//         <Navigate to="/login" replace state={{from: location}}/>
//     );
// };

// export const AdminRoute = ({element:Component}) => {
//     const location = useLocation();
//     return ApiService.isAdmin() ? (
//         Component
//     ):(
//         <Navigate to="/login" replace state={{from: location}}/>
//     );
// };

// export const ProtectedRoute = ({ element: Component }) => {
//   const location = useLocation();
//   const navigate = useNavigate();
//   return ApiService.isAuthenticated() ? Component : navigate("/login");
// };

// export const AdminRoute = ({ element: Component }) => {
//   const location = useLocation();
//   const navigate = useNavigate();
//   return ApiService.isAdmin() ? Component : navigate("/login");
// };

export const ProtectedRoute = ({ element: Component }) => {
  const navigate = useNavigate();

  useEffect(() => {
    if (!ApiService.isAuthenticated()) {
      navigate("/login");
    }
  }, [navigate]);

  return ApiService.isAuthenticated() ? Component : null;
};

export const AdminRoute = ({ element: Component }) => {
  const navigate = useNavigate();

  useEffect(() => {
    if (!ApiService.isAdmin()) {
      navigate("/login");
    }
  }, [navigate]);

  return ApiService.isAdmin() ? Component : null;
};
