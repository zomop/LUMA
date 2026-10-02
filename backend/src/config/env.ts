import 'dotenv/config';
import { z } from 'zod';
export const env = z.object({ DATABASE_URL:z.string().optional(), JWT_SECRET:z.string().min(16).default('dev-only-change-me-please'), PORT:z.coerce.number().default(4000), LUMA_DEVELOPER_EMAIL:z.string().email().default('alyandra5b@gmail.com') }).parse(process.env);
