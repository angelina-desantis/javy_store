# JAVY — Frontend

## Overview

The **JAVY Frontend** is a React + TailwindCSS application that provides the user-facing interface for our ecommerce platform. 

## Development Setup

### 1. Clone the Repository
```
git clone https://github.com/OwenHZhu/EECS4314-Project.git
```

The project root is `EECS4314-Project`.

### 2. Navigate to the Frontend
```
cd frontend
```

### 3. Configure Environment Variables

Copy `.env.example` to `.env`:

```
cp .env.example .env
```

Set the value for:

-   `VITE_BASE_API_URL` — the base URL of the backend API (SpringBoot server).
    

### 4. Install Dependencies
```
npm install
```

### 5. Start the Development Server
```
npm run dev
```

Open the app in your browser:
```
http://localhost:5173
```

### 6. Run Tests
```
npm test
```
