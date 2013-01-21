-- Drop jurassic capabilities table
alter table m drop foreign key fk_m_cap;
alter table m drop column cid;
drop table capabilities;
-- Drop jurassic seqnumber table
drop table seqnumber;
-- Drop old vo_membership request table
drop table vo_membership_req;
