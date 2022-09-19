CREATE TABLE IF NOT EXISTS cars (
                                     id SERIAL PRIMARY KEY,
                                     carName VARCHAR(100),
                                     description TEXT,
                                     carType TEXT,
                                     photo bytea,
                                     created DATE default current_date,
                                     status BOOLEAN default false
);