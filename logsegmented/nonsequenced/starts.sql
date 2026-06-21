explain analyze select s.dept, s.start, s.stop
from empt r, empt s
where r.id <> s.id AND (r.start = s.start AND s.stop < r.stop);
