-- Add all your SQL setup statements here. 

-- When we test your submission, you can assume that the following base
-- tables have been created and loaded with data.  However, before testing
-- your own code, you will need to create and populate them on your
-- SQLServer instance
--
-- Do not alter the following tables' contents or schema in your code.

-- FLIGHTS(fid int primary key, 
--         month_id int,        -- 1-12
--         day_of_month int,    -- 1-31 
--         day_of_week_id int,  -- 1-7, 1 = Monday, 2 = Tuesday, etc
--         carrier_id varchar(7), 
--         flight_num int,
--         origin_city varchar(34), 
--         origin_state varchar(47), 
--         dest_city varchar(34), 
--         dest_state varchar(46), 
--         departure_delay int, -- in mins
--         taxi_out int,        -- in mins
--         arrival_delay int,   -- in mins
--         canceled int,        -- 1 means canceled
--         actual_time int,     -- in mins
--         distance int,        -- in miles
--         capacity int, 
--         price int            -- in $             
--         )

-- CARRIERS(cid varchar(7) primary key,
--          name varchar(83))

-- MONTHS(mid int primary key,
--        month varchar(9));	

-- WEEKDAYS(did int primary key,
--          day_of_week varchar(9));
/*
CREATE TABLE User_chanht1(
    username varchar(20) NOT NULL,
    password varchar(144) NOT NULL,
    balance int NOT NULL,

    PRIMARY KEY (username),
    res_id int NOT NULL UNIQUE REFERENCES Reservation_chanht1(res_id),
);

CREATE TABLE Reserves_chanht1(
    res_id int NOT NULL,
    fid1 int NOT NULL,
    fid2 int NOT NULL,
    paid int NOT NULL,

    itinerarie int NOT NULL, -- although it is not a word, true represents indirect else direct
    PRIMARY KEY (res_id),
    FOREIGN KEY (fid1) REFERENCES Flights(fid),
    FOREIGN KEY (fid2) REFERENCES Flights(fid)
);