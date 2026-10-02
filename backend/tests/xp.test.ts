import { describe,it,expect } from 'vitest';
function levelFor(xp:number){return Math.floor(xp/1000)+1}
describe('LUMA level rules',()=>{it('advances every 1000 XP',()=>{expect(levelFor(820)).toBe(1);expect(levelFor(1000)).toBe(2);expect(levelFor(2500)).toBe(3)})});
