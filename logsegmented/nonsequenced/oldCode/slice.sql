explain select s.dept, s.start, s.stop
from emp s
where (s.start <= 244096 AND s.stop >= 244096);

explain select s.dept, s.start, s.stop
from empt s
where 0 = s.p1
  OR 0 = s.p2
  OR 0 = s.p4
  OR 0 = s.p8
  OR 0 = s.p16
  OR 0 = s.p32
  OR 0 = s.p64
  OR 0 = s.p128
  OR 0 = s.p256
  OR 0 = s.p1x
  OR 0 = s.p2x
  OR 0 = s.p4x
  OR 0 = s.p8x
  OR 0 = s.p16x
  OR 0 = s.p32x
  OR 0= s.p64x
  OR 0= s.p128x
  OR 0= s.p256x
;
explain select s.dept, s.start, s.stop
from empt s
where 0 = s.p1
  OR 0 = s.p2
  OR 0 = s.p4
  OR 0 = s.p8
  OR 0 = s.p16
  OR 0 = s.p32
  OR 0 = s.p64
  OR 0 = s.p128
  OR 0 = s.p256
  OR 0 = s.p512
  OR 0 = s.p1024
  OR 0 = s.p2048
  OR 0 = s.p4096
  OR 0 = s.p8192
  OR 0 = s.p16384
  OR 0 = s.p32768
  OR 0 = s.p65536
  OR 0 = s.p131072
  OR 0 = s.p262144
  OR 0 = s.p524288
  OR 0 = s.p1x
  OR 0 = s.p2x
  OR 0 = s.p4x
  OR 0 = s.p8x
  OR 0 = s.p16x
  OR 0 = s.p32x
  OR 0= s.p64x
  OR 0= s.p128x
  OR 0= s.p256x
  OR 0= s.p512x
  OR 0= s.p1024x
  OR 0= s.p2048x
  OR 0= s.p4096x
  OR 0= s.p8192x
  OR 0= s.p16384x
  OR 0= s.p32768x
  OR 0= s.p65536x
  OR 0= s.p131072x
  OR 0= s.p262144x
  OR 0= s.p524288x


