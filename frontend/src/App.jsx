import React from 'react';
import AnalyzeForm from './components/AnalyzeForm';

export default function App() {
  return (
    <div style={{
      height: '100vh',
      width: '100vw',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      background: 'linear-gradient(to bottom right, #cbd5e0, #a0aec0)',
      fontFamily: 'Segoe UI, Roboto, sans-serif',
    }}>
      <div style={{
        background: '#ffffff',
        padding: '3rem',
        borderRadius: '20px',
        boxShadow: '0 15px 30px rgba(0, 0, 0, 0.1)',
        maxWidth: '700px',
        width: '90%',
        textAlign: 'center',
      }}>
        <h1 style={{
          fontSize: '2.5rem',
          color: '#2d3748',
          marginBottom: '1rem',
        }}>
          Kairos Lens <span role="img" aria-label="magnifying-glass">🔍</span>
        </h1>
        <p style={{
          color: '#4a5568',
          marginBottom: '2.5rem',
          fontSize: '1.1rem',
        }}>
          Enter a news article URL to uncover bias and tone behind the words.
        </p>
        <AnalyzeForm />
      </div>
    </div>
  );
}

