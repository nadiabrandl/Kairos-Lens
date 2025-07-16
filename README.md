# Kairos-Lens
'Kairos Lens' is an AI-powered media bias analyzer designed to help students and young learners identify political bias in online articles. 

It features a neon-flow interface an an animated gauge; meant to be educational and visually engaging.

## Features
- AI-biased media tone & bias analysis
- Political alignment gauge (Left to Right)
- Reflection prompts for critical thinking
- Clean, animated UI designed for school-age users

## Tech Stack
- **Frontend**: React + Vite
- **Backend**: Spring Boot (java)
- **AI**: OpenAI API
- **Web Scraping**: JSoup

## Setup Instructions

```bash
# Backend
cd kairos-lens/backend
./mvnw spring-boot:run

# Frontend
cd kairos-lens/frontend
npm install
npm run dev

