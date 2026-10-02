# LUMA

LUMA is a small technology-oriented social network MVP with an offline Mini LiDAR simulator and an extensible Bluetooth ToF protocol.

## Repository

- `backend/` — TypeScript REST API, Prisma schema, auth, social, messaging, levels and scanner sessions.
- `android/` — Kotlin + Jetpack Compose app shell with offline scanner simulation, Canvas point-cloud rendering, and Bluetooth protocol parsing.
- `docs/` — MVP limitations and API notes.

## Backend setup

1. Copy `.env.example` to `backend/.env` and set `DATABASE_URL` and `JWT_SECRET`.
2. Create a PostgreSQL database named `luma`.
3. Run `cd backend && npm install && npx prisma migrate dev --name init && npm run dev`.
4. API health check: `GET http://localhost:4000/health`.

## Android setup

Open `android/` in Android Studio (Hedgehog or newer), sync Gradle, and run the `app` configuration on an Android device or emulator. The scanner simulator works without internet. Hardware mode is intentionally represented by a safe parser/repository seam for a later Bluetooth permission and device-discovery pass.

## Tests

`cd backend && npm test`

## Security

Secrets are environment variables only. Passwords are bcrypt hashes and password hashes are never serialized in responses. Do not commit `.env`, tokens, keystores, or generated credentials.
