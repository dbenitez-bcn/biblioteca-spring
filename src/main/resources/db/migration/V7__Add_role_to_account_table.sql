ALTER TABLE ACCOUNTS
ADD COLUMN role VARCHAR REFERENCES ACCOUNT_ROLES(role) ON DELETE SET NULL DEFAULT 'USER';