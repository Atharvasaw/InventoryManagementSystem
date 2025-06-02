import React, { useState, useEffect } from "react";
import Layout from "../component/Layout";
import ApiService from "../service/ApiService";
import { useNavigate } from "react-router-dom";

const SupplierPage = () => {
  const [suppliers, setSuppliers] = useState([]);
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const getSuppliers = async () => {
      try {
        const responseData = await ApiService.getAllSuppliers();
        if (responseData.status === 200) {
          setSuppliers(responseData.suppliers);
        } else {
          showMessage(responseData.message);
        }
      } catch (error) {
        showMessage(
          error.response?.data?.message || "Error Getting Suppliers: " + error
        );
        console.log(error);
      }
    };
    getSuppliers();
  }, []);

  const showMessage = (msg) => {
    setMessage(msg);
    setTimeout(() => {
      setMessage("");
    }, 4000);
  };

  const handleDeleteSupplier = async (supplierId) => {
    try {
      await ApiService.deleteSupplier(supplierId);
      setSuppliers(suppliers.filter((supplier) => supplier.id !== supplierId));
      showMessage("Supplier deleted successfully");
    } catch (error) {
      showMessage(
        error.response?.data?.message || "Error Deleting a Supplier: " + error
      );
    }
  };

  return (
    <Layout>
      {message && <div className="message">{message}</div>}
      <div className="supplier-page">
        <div className="supplier-header">
          <h1>Suppliers</h1>
          <div className="add-sup">
            <button onClick={() => navigate("/add-supplier")}>
              Add Supplier
            </button>
          </div>
        </div>
      </div>

      {suppliers.length > 0 ? (
        <ul className="supplier-list">
          {suppliers.map((supplier) => (
            <li className="supplier-item" key={supplier.id}>
              <span>{supplier.name}</span>
              <div className="supplier-actions">
                <button
                  onClick={() => navigate(`/edit-supplier/${supplier.id}`)}
                >
                  Edit
                </button>
                <button onClick={() => handleDeleteSupplier(supplier.id)}>
                  Delete
                </button>
              </div>
            </li>
          ))}
        </ul>
      ) : (
        <p>No suppliers found.</p>
      )}
    </Layout>
  );
};

export default SupplierPage;
