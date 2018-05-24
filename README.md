[![Build Status](https://travis-ci.org/ghacupha/cash.svg?branch=master)](https://travis-ci.org/ghacupha/cash)
[![](https://jitpack.io/v/ghacupha/cash.svg)](https://jitpack.io/#ghacupha/cash)

# cash

A Java light weight monetary representation facade for the rest of us.

Sometimes rather than deal with naked doubles, long integers or even BigDecimals, when it comes to money, as
a programmer, you would rather hide behind some data structure that can represent both money and currency. So you
 simply think of implementing something like this :

```java

class Money{

  private final double amount;

  private final String currency;

  Money(double amount, String currency){

    this.amount = amount;
    this.currency = currency;
  }

  // Getters and setters

  // Many many operations

  @Override
  String toString(){

    return this.currency + " " + amount;
  }

  // Hashcode and his brother Equals
```


So smart. Working with money would be so easy ...
```java
    // Awesome example

    // Make money
    Money pocketMoney = new Money(500, "GNU");

    // Add money
    Money moreMoney = new Money(10, "USD")
    pocketMoney.plus(moreMoney);

```

*"Nothing"* wrong with that except you now have take care of the operations yourself, additions,
subtractions, divisions and multiplications. You have to create these in operational methods in your **Money**
 class using the exact level of accuracy that would make an accountant in your locale happy.
This accountant-hapiness is locale-dependent bacause your data structure needs to have an
accuracy of 2 decimal places in some countries and 3 decimal places in others. Some accountants
from some [countries](https://en.wikipedia.org/wiki/Non-decimal_currency) will frown at your
decimal places. You will quickly make the accountant your enemy when he discovers your loose
definition of *"rounding off"*, or when he discovers you had not thought about it (´•_•`)

#### Ok so what then?
Create a better data structure, make decimal places configurable, make sure your *"rounding off"*
algorithm does not chew people's money. And you need to make that configurable too, inorder to
address the needs of certain types of accountants (´•_•`) and while you are at it be sure to make
your currency, ISO 4217:2015 compliant. One other thing, "GNU" you used up there in your "awesome example"...
that's not a currency code. Anywhere. It should not be possible to add it to your 10 dollars without some
currency conversion logic somewhere. Did you do that? You know... convert the currencies?
Or even check for mismatch? Which exceptions did you throw?

Using double types as monetary variables, will give you guaranteed heights of pain you have never felt.
And, if you have used double to represent money in an application that our grumpy accountant will use,
please don't ever share your address with him. They will not find your body.

#### What is ISO 4217:2015?
Very well, have you heard of [joda-money](http://www.joda.org/joda-money/)? You should give it a try.
This library can do all of the above and more.

Check this out :
```java
    // create a monetary value
      Money money = Money.parse("USD 23.87");

      // add another amount with safe double conversion
      CurrencyUnit usd = CurrencyUnit.of("USD");
      money = money.plus(Money.of(usd, 12.43d));

      // subtracts an amount in dollars
      money = money.minusMajor(2);

      // multiplies by 3.5 with rounding
      money = money.multipliedBy(3.5d, RoundingMode.DOWN);

      // compare two amounts
      boolean bigAmount = money.isGreaterThan(dailyWage);

      // convert to GBP using a supplied rate
      BigDecimal conversionRate = ...;  // obtained from code outside Joda-Money
      Money moneyGBP = money.convertedTo(CurrencyUnit.GBP, conversionRate, RoundingMode.HALF_UP);

      // use a BigMoney for more complex calculations where scale matters
      BigMoney moneyCalc = money.toBigMoney();
```

So the moral of the story is, use joda-money library, don't reinvent the wheel

#### Dude you are reinventing the wheel!!!

Couldn't get anything past you, could I? Ok for the record, what we are doing here is not re-inventing the wheel. Just
simply taking the wheel and adding some grooves to make it usable and resusable for some very specific conditions.
In fact [cash](https://github.com/ghacupha/cash) would not be better off for you.
Stick with joda-money, this is my wheel.

**Hint:** "light-weight" does not mean *faster*, this is
just a library I can manipulate to meet the precise abstraction requirements of [another](https://github.com/ghacupha/fassets)
application and [others](https://github.com/ghacupha/book-keeper) on the way. This is just a facade which in
fact has lesser functionality that the [library](http://www.joda.org/joda-money/) on the shoulders of which it rides.

#### Ok how does it work?
This library is designed for the lazy, with static initializers for common currencies (USD, GBP, EUR, KES)
and others that would be very useful in the book-keeper application. Typically creating monetary
amounts might look like this :

```java

   // somewhere in the begining
   import static io.github.ghacupha.cash.HardCash.dollar;
   import static io.github.ghacupha.cash.HardCash.euro;
   import static io.github.ghacupha.cash.HardCash.shilling
   import static io.github.ghacupha.cash.HardCash.sterling

   // some other code and what not...

    // Cash starts here
    Cash twentyPounds = sterling(20);
    Cash fiveEuroes = euro(5);
    Cash thrityShillings = shilling(30);
    Cash tenDollars = dollars(10);
```

I think those initializers are pretty neat.

**Don't see your currency?**

Other currencies are yet to be implemented. And are not needed for now. But a PR with these will not be frowned upon.
Anyway, you could still initialize a currency by simply providing the correct ISO 4217 code for that currency. Yes you will have
to read up on that buddy ㋛

```java

    Cash twentyPounds = HardCash.of(20, "GBP")
    Cash twentyEuroes = HardCash.of(20, "EUR")
    Cash twentYDollars = HardCash.of(20, "USD")
    Cash twentYShillings = HardCash.of(20, "KES")
```

Now, am going to illustrate the rest of the functions through the unit tests already in the libary. That's right, I even tested the
facade. Am that guy...

```java
@DisplayName("General use cash test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CashTest {

    private Cash cash;
    private final static Currency KES = Currency.getInstance("KES");
    private final static Currency USD = Currency.getInstance("USD");
    private final static Currency EUR = Currency.getInstance("EUR");
    private final static Currency GBP = Currency.getInstance("GBP");

    @BeforeAll
    void setUp() throws Exception {

        cash = shilling(106.50);
    }

    @Test
    @DisplayName("Check if #isMoreThan method is logically correct")
    void isMoreThan() throws Exception {

        assertTrue(cash.isMoreThan(shilling(100)));
    }

    @Test
    @DisplayName("Check if #isMoreThan method is logically correct if the difference is 0.01")
    void isMoreThan1() throws Exception {

        assertTrue(cash.isMoreThan(HardCash.of(106.49,"KES")));
    }

    @Test
    @DisplayName("Check if #isMoreThan method will throw exception if compared with non-similar denomination")
    void cannotCashWithDifferentCurrencies() throws Exception {

        /*try {
            cash.isMoreThan(HardCash.of(106.49,"USD"));
            fail( "My method didn't throw CurrencyMismatchException when I expected it to" );
        } catch (CurrencyMismatchException e) {
            e.printStackTrace();
        }*/

        // Much better, thank God for junit 5
        assertThrows(CurrencyMismatchException.class, () -> cash.isMoreThan(HardCash.of(106.49,"USD")));
    }

    @Test
    @DisplayName("Check if #isLessThan method is logically correct")
    void isLessThan() throws Exception {

        assertTrue(cash.isLessThan(HardCash.of(106.51,"KES")));
    }


    @Test
    @DisplayName("Additions test")
    void plus() throws Exception {

        assertEquals(shilling(206.53),cash.plus(shilling(100.03)));
    }

    @Test
    @DisplayName("Subtractions test")
    void minus() throws Exception {

        assertEquals(shilling(105.00),cash.minus(HardCash.of(1.5,"KES")));
    }

    @Test
    @DisplayName("Multiplications test")
    void multiply() throws Exception {

        assertEquals(shilling(319.50),cash.multiply(3));
        assertEquals(shilling(319.50),cash.multiply(3, RoundingMode.HALF_EVEN));
        assertEquals(shilling(319.50),cash.multiply(3, RoundingMode.HALF_DOWN));
    }

    @Test
    @DisplayName("Divisions test")
    void divide() throws Exception {

        assertEquals(shilling(32.08),cash.divide(3.32));
        assertEquals(shilling(32.08),cash.divide(3.32,RoundingMode.HALF_DOWN));
        assertEquals(shilling(32.27),cash.divide(3.3,RoundingMode.HALF_EVEN));
    }

    @Test
    @DisplayName("Absolute number tests")
    void ABSworks() {
        assertEquals(dollar(32.08),dollar(-32.08).abs());
    }
}

```

###### Supported operations
So there. The following operations should work :
- Addition ( using #plus() method)
- Subtraction ( using #minus() method)
- Multiplication (using #multiply() method)
- Division (using #divide() method)
- Compare #isMoreThan() and #isLessThan
- Convert to absolute (don't ask) with #abs() method

##### But if I use this in my entities, they will not be persistent... Will they?
Dude, you are still here? This library was not meant for you! Very well if you must, and if,
your entities are based on JPA 2.0, and hibernate, with spring, you could use attribute converters like so:

```java

/**
 * Used to convert Cash object state to database column representation and back again
 *
 * @author edwin.njeru
 */
@Converter(autoApply = true)
public class CashAttributeConverter implements AttributeConverter<Cash, String> {

    @Override
    public String convertToDatabaseColumn(Cash attribute) {

        ReadableCash cash = new ReadableHardCash(attribute);

        return cash.getString();
    }

    @Override
    public Cash convertToEntityAttribute(String dbData) {

        return ReadableHardCash.parse(dbData);
    }

```

Please note that if the above implementation is put in a package that is discoverable by spring and
hibernate, you could use the cash interface to represent money **anywhere** in your entities. This
has been tested and it works. And is currently being used [some](https://github.com/ghacupha/fassets)
applications to implement persitent accounts.

##### Did you say accounts? Like book keeping?
Hehehe! An [abstraction](https://github.com/ghacupha/book-keeper) for another day

#### Okay, you sold me. How do I install it?
I was not selling you. Seriously, I mean it, don't use this library! Very well if you are going to ignore
everything I tell you, you might download this with maven using jitpack repository like so:
```xml
<repositories>
  <repository>
   <id>jitpack.io</id>
   <url>https://jitpack.io</url>
  </repository>
</repositories>

<!-- Dependencies -->
<dependency>
  <groupId>com.github.ghacupha</groupId>
  <artifactId>cash</artifactId>
  <version>1.0.1</version>
</dependency>

```

###### To install from source
**Requirements**
 - Java 8. Seriously why would you be using version 6 in 2018?
 - Maven version 3.5.3 => This is enforced in the POM. You can change to the version in your system but i could
 not guarantee you successful builds
 - GIT, obviosly

 in your favourite work folder do this:
 ```
    git clone https://github.com/ghacupha/cash.git

    cd cash

    mvn clean package

    mvn install

    #Done
 ```

Then now you could add it from your local maven repository like so:
```xml
<!-- Dependencies -->
<dependency>
  <groupId>com.github.ghacupha</groupId>
  <artifactId>cash</artifactId>
  <version>1.0.1</version>
</dependency>
```

## TODO

- Implement FastCash which is a Money model implementation of the Cash interface backed by long integer,
 apparently believed by some authorities to be faster than BigDecimal
- Implement more static initializers

## Contact

Feedback and contributions are welcome.
Feel free to send an [email](mailto:mailnjeru@gmail.com) or submit a pull request.

## License

This code is free to use under the terms of the [LGPL v3 license](https://opensource.org/licenses/lgpl-3.0.html).
