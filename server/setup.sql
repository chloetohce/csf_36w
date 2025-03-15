use temp;

drop table if exists posts;

create table posts (
    pid varchar(8) not null,
    comments mediumtext,
    picture mediumblob,

    constraint pk_pid primary key(pid)
);

grant all privileges on temp.* to 'chloe'@'%';
flush privileges;