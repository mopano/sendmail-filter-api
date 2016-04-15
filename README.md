# Sendmail Mail Filter API

This package is the base API provider for mail filters (also known as milters) for the
 [Sendmail Filter Runner](https://www.github.com/mopano/sendmail-filter-runner).
 The Sendmail Milter Protocol is also used by Postfix.

## Quick how-to

Requires [Apache Maven](https://maven.apache.org).

Dependency coordinates:  
groupId: com.mopano  
artifactId: sendmail-filter-api  
version: 2.0.0  
scope: provided


1. Clone and run `mvn install` (Optional, if it doesn't get approved for Maven Central repository publishing)
2. Create a new Maven project and add it as a dependency with the `provided` scope.
3. Implement the `com.sendmail.milter.IMilterHandler` interface directly or by extending the
 `com.sendmail.milter.AMilterHandlerAdapter` abstract class with your desired logic.
4. Implement the `com.sendmail.milter.spi.IMilterHandlerFactory` with a `newInstance()` method
returning an a new fully-implemented handler.
5. Create a text file named `com.sendmail.milter.spi.IMilterHandlerFactory` in a `META-INF/services`
directory, preferably in your resource path (by default in Maven and Gradle it's `src/main/resources`),
and put in one line with the fully-qualified name of your `IMilterHandlerFactory` implementation.
You may put in comment lines starting with `#`.
6. Configure your filter runner as specified on its page.

## Implementing IMilterHandler

Although the JavaDocs should be documented well enough, here's a run-down of the API you will need to know.
You can find them on the [gh-pages](https://mopano.github.io/sendmail-filter-api) branch.

#### From the IMilterHandler interface
```
    public IMilterStatus connect(String hostname, InetAddress hostaddr, Properties properties);
    public IMilterStatus helo(String helohost, Properties properties);
    public IMilterStatus envfrom(byte[][] argv, Properties properties);
    public IMilterStatus envrcpt(byte[][] argv, Properties properties);
    public IMilterStatus header(byte[] name, byte[] value);
    public IMilterStatus eoh(IMilterActions eohActions, Properties properties);
    public IMilterStatus data(Properties properties);
    public IMilterStatus body(ByteBuffer bodyp);
    public IMilterStatus eom(IMilterActions eomActions, Properties properties);
    public IMilterStatus abort();
    public IMilterStatus close();
    public IMilterStatus unknown(byte[] command, Properties properties);
    public void reset();
    public int getActionFlags();
    public int getProtocolFlags();
    public int negotiateVersion(int mtaVersion, int actionFlags, int protocolFlags);
    public Map<Integer, Set<String>> getMacros();
```
Of these the only ones you are required to implement are `getActionFlags` and `negotiateVersion`.
Everything else can be inherited from `AMilterHandlerAdapter`.

Although presuming the values of `argv` in `envfrom`, `envrcpt`, and the name and value in the `header`
calls to be of ASCII-compatible encoding is generally safe, you are given the option to decode it to a
String on your own. The easiest way is using `new String(value)`. Headers however may come with values
encoded using [RFC 2047](https://www.ietf.org/rfc/rfc2047.txt.pdf),
[RFC 2231](https://www.ietf.org/rfc/rfc2231.txt.pdf) or even just unspecified non-ASCII, which
is the most unpredictable case, so instead of sending you potentially malformed String data, the API
gives you the raw string data.
