## Notes
Depends on the official flink-connecner-rabbitMQ package, which does not provide the Table API, so the related functionality is added.
## Example

### Sink:

``` 
create table rabbit (id int) with ('connector'= 'rabbitmq', 'exchange-name'='test', 
 'host'='127.0.0.1', 'port'='5672', 'username'='user', 'password'='123', 
 'format'='json', 'routing-key'='test', 'virtual-host'='/test/') ");

 insert into rabbit select * from (values(1)) ;
 ```

### Source:
```
create table rabbit (id int) with ('connector'= 'rabbitmq', 'queue-name'='test', 
 'host'='127.0.0.1', 'port'='5672', 'username'='user', 'password'='123',  
 'format'='json', 'virtual-host'='/test/');

create table sink_table (id int) with ('connector'='print') ;

insert into sink_table select * from rabbit
```

## Download address
```
<dependency>
  <groupId>io.github.jeff-zou</groupId>
  <artifactId>flink-sql-connector-rabbitmq</artifactId>
  <version>1.15.1</version>
</dependency>
```
