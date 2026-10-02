import jwt from 'jsonwebtoken'; import { env } from '../config/env.js'; import type { Request, Response, NextFunction } from 'express';
export type AuthRequest = Request & { userId?: string };
export function signToken(id:string){ return jwt.sign({sub:id}, env.JWT_SECRET,{expiresIn:'7d'}); }
export function requireAuth(req:AuthRequest,res:Response,next:NextFunction){ const token=req.headers.authorization?.replace('Bearer ',''); if(!token) return res.status(401).json({error:'Authentication required'}); try{ req.userId=String(jwt.verify(token,env.JWT_SECRET).sub); next(); }catch{return res.status(401).json({error:'Invalid or expired token'});} }
