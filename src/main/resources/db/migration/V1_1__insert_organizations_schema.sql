-- sample.organizations test data

INSERT INTO organizations (id, name, instituition_name, country, cep, state, city, number, street) 
VALUES (NEXT VALUE FOR organizations_seq, 'Organization Name', 'Institution Name', 'Country', 'CEP', 'State', 'City', 'Number', 'Street');