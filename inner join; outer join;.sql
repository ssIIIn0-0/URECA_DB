use world;
desc country;

-- char(고정 너비) vs varchar(가변 너비)
-- data import / export
-- foreign key ( identifying, non-identifying )
-- 1. country table 의 모든 자료를 조회하시오. (239)
select * from country;

-- 2. country table 의 전체 건수를 구하세요 (1)
select count(*) from country; -- 239 건

-- 3. country table 에서 국가 code가 FRA 인 자료를 조회하시오. (1)
select * from country where code = 'FRA';

-- 4. country table 에서 대륙(Continent) 이 Africa 또는 Europe 인 자료를 조회하시오. (104)
select * from country where continent = 'Africa' or continent = 'Europe';
select * from country where continent in ('Africa','Europe');

-- 5. country table 에서 독립일(IndepYear) 이 없는 나라의 자료를 조회하시오. (47)
select * from country where indepyear is null;
select * from country where indepyear is not null;

-- 6. country table 에서 모든 독립일을 중복 없이 조회하시오.
select distinct(indepyear) from country;  -- null 포함 (89) : distinct 는 null 도 하나의 value 로 처리
select distinct(indepyear) from country where indepyear is not null; -- (88)

-- 7. country table 에서 인구(Population) 이 1000000(백만) 보다 크고 수명예상(LifeExpectancy) 이 70 살 이상인 자료를 조회하시오. (66)
select * from country where Population > 1000000 and LifeExpectancy >= 70;

-- 8. country table 에서 이전 gnp(GNPOld) 대비 gnp 가 1000 이상 증가한 국가의 이름과 gnp, gnpold, 증가한 GNP 를 조회하시오.
--    이 때 증가한 GNP 를 GNPDiff 로 alias 를 주세요. (47)
select gnp, gnpold, (gnp-gnpold) GNPDiff from country where (gnp - gnpold) >= 1000;

-- 9. 위 데이터를 GNPDiff 로 내림차순 정렬하세요. (47)
select gnp, gnpold, (gnp-gnpold) GNPDiff from country where (gnp - gnpold) >= 1000 order by GNPDiff;

-- 10. country table 에서 GNP 가 100 미만 또는 100000 초과인 자료를 조회하세요. (68)
select * from country where gnp < 100 or gnp > 100000; 

-- 11. country table 에서 GNP 가 100 초과하고 100000 미만인 자료를 조회하세요. (170)
select * from country where gnp > 100 and gnp < 100000; 

-- 12. country table 에서 GNP 가 100 이상 100000 이하인 자료를 조회하세요. (171)
select * from country where gnp >= 100 and gnp <= 100000; 
select * from country where gnp between 100 and 100000; 

-- 13. 위 11, 12 차이를 만드는 나라의 데이터를 확인하세요.
select * from country where gnp = 100 or gnp = 100000; 

-- 14. country table 에서 독립년도(IndepYear) 가 1980 이후이면서 대륙(Continent) 이 Asia 가 아닌 나라의 자료를 조회하세요. (23)
select * from country where indepyear >= 1980 and Continent != 'Asia';
select * from country where indepyear >= 1980 and not Continent = 'Asia';
select count(*) from country where indepyear >= 1980; -- 32건
select count(*) from country where indepyear >= 1980 and Continent = 'Asia'; -- 9건

-- 15. country table 에서 대륙(Continent) 이  'Europe', 'Asia', 'North America' 이 아닌 나라의 자료를, 
--     대륙(Continent) 기준으로 오름차순, GNP 기준 내림차순으로 국가명, 대륙명, GNP 를 조회하시오. (105)
select name, continent, gnp from country where Continent not in ('Europe', 'Asia', 'North America') 
   order by Continent, gnp desc;
   
-- 16. city table 에서 도시명에 'S' 로 시작하고 중간에 'aa' 가 포함되는 나라를 모두 조회하세요. (4)
select * from city where name like 'S%aa%';

-- 17. country table 에서 가장 면적이 큰 5개 나라의 이름, 대륙, 면적을 조회하세요.
select name, Continent, SurfaceArea from country order by SurfaceArea desc limit 5; -- mysql

-- 18. 위 쿼리의 결과를 보고 5개 나라 뒤 10개 나라 (6 ~ 15 위) 를 조회
select name, Continent, SurfaceArea from country order by SurfaceArea desc limit 10 offset 5; -- mysql
select name, Continent, SurfaceArea from country order by SurfaceArea desc limit 10, 5; -- 앞 숫자가 offset 뒤 숫자가 limit
select name, Continent, SurfaceArea from country order by SurfaceArea desc limit 5, 10; -- 앞 숫자가 offset 뒤 숫자가 limit
