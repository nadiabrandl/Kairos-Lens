import React, { useState } from 'react';
import PoliticalGauge from './PoliticalGauge';

const AnalyzeForm = () => {
  const [url, setUrl] = useState('');
  const [response, setResponse] = useState(null);
  const [error, setError] = useState(null);

  const handleReset = () => {
    setUrl('');
    setResponse('');
    setError('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setResponse(null);

    try {
      const res = await fetch('http://localhost:8080/api/analyze', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({article: url}),
      });

      if (!res.ok) {
        throw new Error(`Server responded with status ${res.status}`);
      }

      const data = await res.json();
      setResponse(data);
      console.log("👉 Response from backend:", data);

    } catch (err) {
      console.error('Error analyzing article:', err);
      setError('Something went wrong while analyzing. Please try again.');
    }
  };

  return (
      <div
        style={{
          minHeight: '100vh',
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'center',
          alignItems: 'center',
          background: 'linear-gradient(135deg, #0f0c29, #302b63, #24243e)',
          color: '#f8f8f8',
          fontFamily: 'Poppins, sans-serif',
          padding: '2rem'
        }}
      >
        <h2 style={{
          fontSize: '3rem',
          fontWeight: '700',
          color: '#00ffe7',
          textShadow: '0 0 10px #00ffe7, 0 0 20px #00ffe7'
        }}>
          Analyze Media Bias
        </h2>

        <form onSubmit={handleSubmit} style={{ marginTop: '2rem', display: 'flex', alignItems: 'center', justifyContent: 'center', gap: '1rem'}}>
          <input
            type="text"
            placeholder="Enter article URL..."
            value={url}
            onChange={(e) => setUrl(e.target.value)}
            style={{
              padding: '0.75rem 1rem',
              width: '300px',
              border: 'none',
              borderRadius: '8px',
              backgroundColor: '#1e1e2f',
              color: '#f8f8f8',
              fontSize: '1rem',
              boxShadow: '0 0 10px rgba(0, 255, 231, 0.4)',
              outline: 'none'
            }}
          />
          <button
            type="submit"
            style={{
              marginLeft: '1rem',
              padding: '0.75rem 1.5rem',
              backgroundColor: '#00ffe7',
              color: '#0f0c29',
              border: 'none',
              borderRadius: '8px',
              fontWeight: '600',
              fontSize: '1rem',
              cursor: 'pointer',
              boxShadow: '0 0 10px #00ffe7, 0 0 20px #00ffe7'
            }}
          >
            Analyze
          </button>
          <button
          type="button"
          onClick={handleReset}
          style={{
            padding: '0.75rem 1.5rem',
            backgroundColor: '#00ffe7',
            color: '#0f0c29',
            border: 'none',
            borderRadius: '8px',
            fontWeight: '600',
            fontSize: '1rem',
            cursor: 'pointer',
            boxShadow: '0 0 10px #00ffe7, 0 0 20px #00ffe7'
           }}
  >
    Reset
  </button>
        </form>

        {/* Always render the gauge */}
        <PoliticalGauge alignment={response?.politicalAlignment || null} />
       
        {response && (
  <>
    <div style={{
      marginTop: '2rem',
      backgroundColor: '#1e1e2f',
      padding: '2rem',
      borderRadius: '10px',
      boxShadow: '0 0 15px rgba(0,255,231,0.5)',
      width: '100%',
      maxWidth: '600px',
      textAlign: 'left',
      color: '#f8f8f8'
    }}>
      <h3 style={{ color: '#00ffe7', marginBottom: '1rem' }}>🧠 Bias Analysis Results</h3>

      <p><strong style={{ color: '#00ffe7' }}>Bias Summary:</strong><br />
        {response.biasSummary}
      </p>

      <p style={{ marginTop: '1rem' }}><strong style={{ color: '#00ffe7' }}>Tone:</strong><br />
        {response.tone}
      </p>

      <p style={{ marginTop: '1rem' }}><strong style={{ color: '#00ffe7' }}>Reflection Prompt:</strong><br />
        {response.reflectionPrompt}
      </p>
    </div>
  </>
)}

        {error && (
          <div style={{ color: '#ff4c4c', marginTop: '1rem' }}>
            ❌ {error}
          </div>
        )}
      </div>
    );    
};

export default AnalyzeForm;
