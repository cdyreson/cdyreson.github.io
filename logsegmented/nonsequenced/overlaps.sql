explain analyze (select r.dept, greatest(r.start,s.start), least(r.stop,s.stop), 
 r.start, r.stop, s.start, s.stop
from emp r, emp s
where   s.id <> r.id AND ((r.start <= s.start AND s.start < r.stop) 
              OR (s.start <= r.start AND r.start < s.stop)))