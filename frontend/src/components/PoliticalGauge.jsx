import React from 'react';

const alignmentLevels = ['LEFT', 'CENTER_LEFT', 'CENTER', 'CENTER_RIGHT', 'RIGHT'];

const PoliticalGauge = ({ alignment }) => {
  const alignmentUpper = alignment?.toUpperCase();
const position = alignmentUpper && alignmentLevels.includes(alignmentUpper)
  ? alignmentLevels.indexOf(alignmentUpper)
  : -1;


  const getLabel = (label) => {
    return label.replace('_', ' ').toLowerCase().replace(/\b\w/g, l => l.toUpperCase());
  };

  const getFillWidth = () => {
    if (position === -1) return '0%';
    return `${(position / (alignmentLevels.length - 1)) * 100}%`;
  };

  return (
    <div style={{ width: '100%', marginTop: '3rem', textAlign: 'center' }}>
      <h3 style={{
        color: '#00ffe7',
        textShadow: '0 0 5px #00ffe7, 0 0 15px #00ffe7',
        fontSize: '1.8rem',
        marginBottom: '1rem',
      }}>
        Political Alignment
      </h3>

      <div style={{
        position: 'relative',
        height: '40px',
        borderRadius: '30px',
        backgroundColor: '#111',
        margin: '0 auto',
        width: '90%',
        maxWidth: '600px',
        overflow: 'hidden',
        border: '1px solid #333',
      }}>
        {/* Fill bar only if alignment is present */}
        {position >= 0 && (
          <div style={{
            height: '100%',
            width: getFillWidth(),
            background: 'linear-gradient(to right, #ff4c4c, #ffa500, #f8f8f8, #00e0ff, #00ffe7)',
            borderRadius: '30px',
            transition: 'width 0.6s ease-in-out'
          }} />
        )}

        {/* Arrow only if alignment is present */}
        {position >= 0 && (
          <div
            style={{
              position: 'absolute',
              top: '-10px',
              left: `${(position / (alignmentLevels.length - 1)) * 100}%`,
              transform: 'translateX(-50%)',
              color: '#00ffe7',
              fontSize: '1.5rem',
              textShadow: '0 0 10px #00ffe7, 0 0 20px #00ffe7',
              transition: 'left 0.4s ease-in-out'
            }}
          >
            ▲
          </div>
        )}
      </div>

      <div style={{
        display: 'flex',
        justifyContent: 'space-between',
        padding: '0.5rem 2rem',
        marginTop: '0.5rem',
        color: '#ccc',
        fontSize: '0.95rem',
        textTransform: 'uppercase',
        fontFamily: 'Poppins, sans-serif',
      }}>
        {alignmentLevels.map((label, idx) => {
          const isSelected = alignment?.toUpperCase() === label;
          return (
            <span
              key={idx}
              style={{
                color: isSelected ? '#00ffe7' : '#888',
                fontWeight: isSelected ? '700' : '400',
                textShadow: isSelected ? '0 0 5px #00ffe7, 0 0 15px #00ffe7' : 'none',
              }}
            >
              {getLabel(label)}
            </span>
          );
        })}
      </div>
    </div>
  );
};

export default PoliticalGauge;
