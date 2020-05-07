# blaze-graalvm-test
Test blaze http server build for graalvm native-image

Working native-image build for MacOS:

```
$ sbt assembly
$ native-image  -H:+ReportExceptionStackTraces  --allow-incomplete-classpath --no-fallback --initialize-at-build-time --enable-http --enable-https --enable-all-security-services --verbose -jar "./target/scala-2.13/blaze-graal-ni-test-assembly-0.1.0-SNAPSHOT.jar" projectBinaryImage
```

On linux you can add `--static` param.
After build you can run `./projectBinaryImage`, you can pass additional JVM params with -J flag. Maybe you should also add `-H:UseMuslC="/path.to/muslC"` param, see references links.

`./projectBinaryImage -J-Xmx3g`

Benchmarks on my MBP 15 2017:

Native-image -J-Xmx3g:
```
$ wrk -d10 -c150 --latency "http://localhost:8081/plaintext"
Running 10s test @ http://localhost:8081/plaintext
  2 threads and 150 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.55ms  311.62us   8.47ms   90.46%
    Req/Sec    48.11k     3.30k   55.31k    69.80%
  Latency Distribution
     50%    1.52ms
     75%    1.62ms
     90%    1.75ms
     99%    2.40ms
  966878 requests in 10.10s, 119.87MB read
  Socket errors: connect 0, read 77, write 0, timeout 0
Requests/sec:  95707.35
Transfer/sec:     11.87MB
(instant)
```

GraalVM -Xmx3g:
```

$ wrk -d10 -c150 --latency "http://localhost:8081/plaintext"
Running 10s test @ http://localhost:8081/plaintext
  2 threads and 150 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.42ms  187.92us   6.10ms   88.66%
    Req/Sec    52.50k     2.37k   56.16k    79.70%
  Latency Distribution
     50%    1.37ms
     75%    1.45ms
     90%    1.61ms
     99%    2.11ms
  1055139 requests in 10.10s, 130.81MB read
  Socket errors: connect 0, read 100, write 0, timeout 0
Requests/sec: 104435.64
Transfer/sec:     12.95MB
(after warm-up)
```




References:
https://github.com/drocsid/http4s/blob/docs/deployment/docs/src/main/tut/deployment.md