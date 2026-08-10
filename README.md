# 🚌 GoShuttle

[![Vue 3](https://img.shields.io/badge/Vue-3-4FC08D?logo=vue.js)](https://vuejs.org/)
[![Vite](https://img.shields.io/badge/Vite-4-646CFF?logo=vite)](https://vitejs.dev/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A modern, responsive web application for managing and tracking campus shuttle bus services. Built with Vue 3 and designed with a sleek glass‑morphism aesthetic, GoShuttle provides students and staff with real‑time bus tracking, schedule viewing, stop management, and digital pass functionality.

> **Note:** This repository currently contains the complete **frontend** application. The backend API (planned to be built with Go, hence the project name) is under development and will be integrated in future updates.

---

## ✨ Features

- **Live Bus Tracking** – View real‑time route progress and bus capacity in a clean, schematic interface.
- **Bus Schedules** – Search and filter schedules by route, status, and departure times. Expandable cards show detailed stop‑by‑stop information.
- **Stops & Routes** – Browse all stops and routes. Click a stop to set it as your preferred stop, with a banner linking directly to the schedule page.
- **My Pass** – View your digital pass card with usage stats, animated counters, and a copy‑to‑clipboard feature for your pass ID.
- **User Authentication** – Sign in and register with real‑time field validation (CPUT email required, password ≥ 8 characters).
- **Glass‑morphism UI** – Consistent blurred‑glass design language across all pages, with a shared sticky header and footer.
- **Fully Responsive** – Works seamlessly on desktop, tablet, and mobile devices.

---

## 🛠️ Tech Stack

| Area        | Technology                                                   |
|-------------|--------------------------------------------------------------|
| Framework   | [Vue 3](https://vuejs.org/) (Composition API)                |
| Routing     | [Vue Router 4](https://router.vuejs.org/)                    |
| Build Tool  | [Vite](https://vitejs.dev/)                                  |
| Styling     | Plain CSS with custom design tokens (no external UI libraries) |
| Language    | JavaScript (ES6+)                                            |

---

## 📁 Project Structure



## 🚀 Getting Started

### Prerequisites

- [Node.js](https://nodejs.org/) (v16 or higher)
- [npm](https://www.npmjs.com/) or [yarn](https://yarnpkg.com/)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Gift1400/GoShuttle.git
   cd GoShuttle/frontend

   Install dependencies

## bash
```bash
npm install
Run the development server

## bash
npm run dev
Open your browser and navigate to the local URL printed by Vite (typically http://localhost:5173).

## Build for Production
```bash
bash
npm run build
