# CPF and CNPJ utility library

[![Maven Central](https://img.shields.io/maven-central/v/io.github.felseje/cpf-cnpj-utils)](https://search.maven.org/artifact/io.github.felseje/cpf-cnpj-utils)
[![License: BSD 3-Clause](https://img.shields.io/badge/License-BSD%203--Clause-blue.svg)](https://opensource.org/licenses/BSD-3-Clause)
![Java](https://img.shields.io/badge/Java-17%2B-blue?logo=java&logoColor=white)

A tiny, zero-dependency library to generate, validate and format Brazilian CPFs and CNPJs — including the future *
*alphanumeric CNPJ** format (2026-ready).

---

## ✨ Features

- ✅ Validates CPF and CNPJ (numeric & alphanumeric)
- 🔄 Generates valid CPF and CNPJ for test purposes
- 🧼 Normalizes and formats values with ease
- ✨ Pure Java - no dependencies
- 🇧🇷 Built for Brazilian identification formats (CPF and CNPJ)

---

## 🚀 Installation

This library requires minimum Java 17 to work.
To use, add to your `pom.xml`:

```xml

<dependency>
    <groupId>io.github.felseje</groupId>
    <artifactId>cpf-cnpj-utils</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

## 💡 Usage Examples

### 🔄 Generation

```java
import io.github.felseje.cpf.CpfUtils;
import io.github.felseje.cnpj.CnpjUtils;

public class Main {
    public static void main(String[] args) {
        // CPF generation
        System.out.println(CpfUtils.generate()); // 01234567890
        System.out.println(CpfUtils.generate(true)); // 012.345.678-90

        // CNPJ generation
        System.out.println(CnpjUtils.generate(CnpjType.NUMERIC)); // 00000000000191
        System.out.println(CnpjUtils.generate(CnpjType.NUMERIC, true)); // 00.000.000/0001-91

        System.out.println(CnpjUtils.generate(CnpjType.ALPHANUMERIC)); // 12ABC34501DE35
        System.out.println(CnpjUtils.generate(CnpjType.ALPHANUMERIC, true)); // 12.ABC.345/01DE-35
    }
}
```

### ✅ Validation

```java
import io.github.felseje.cpf.CpfUtils;
import io.github.felseje.cnpj.CnpjUtils;

public class Main {
    public static void main(String[] args) {
        // CPF validation
        System.out.println(CpfUtils.isValid("01234567890")); // true
        System.out.println(CpfUtils.isValid("012.345.678-90")); // true

        CpfUtils.validate("00000000000"); // throws InvalidCpfException
        CpfUtils.validate("000.000.000-00"); // throws InvalidCpfException

        // Numeric CNPJ validation
        System.out.println(CnpjUtils.isValid("00000000000191")); // true
        System.out.println(CnpjUtils.isValid("00.000.000/0001-91")); // true

        CnpjUtils.validate("00000000000000"); // throws InvalidCnpjException
        CnpjUtils.validate("00.000.000/0000-00"); // throws InvalidCnpjException

        // Alphanumeric CNPJ validation
        System.out.println(CnpjUtils.isValid("12ABC34501DE35")); // true
        System.out.println(CnpjUtils.isValid("12.ABC.345/01DE-35")); // true

        CnpjUtils.validate("AAAAAAAAAAAAAA", CnpjType.NUMERIC); // throws InvalidCnpjException
        CnpjUtils.validate("AA.AAA.AAA/AAAA-00", CnpjType.ALPHANUMERIC); // throws InvalidCnpjException
    }
}
```

### 🧹 Normalization

```java
import io.github.felseje.cpf.CpfUtils;
import io.github.felseje.cnpj.CnpjUtils;

public class Main {
    public static void main(String[] args) {
        // CPF normalization
        System.out.println(CpfUtils.clear("A012.B345.C678-D90E")); // 01234567890
        System.out.println(CpfUtils.normalize("012.345.678-90")); // 01234567890
        System.out.println(CpfUtils.normalize("FAIL")); // throws InvalidCpfException


        // CNPJ normalization
        System.out.println(CnpjUtils.clear("&A12A.A345A.A678A/A0001A-A95A&")); // A12AA345AA678AA0001AA95A
        System.out.println(CnpjUtils.normalize("&12&.&345&.&678&/&0001&-&95&")); // 12345678000195
        System.out.println(CnpjUtils.normalize("@12&.&ABC&.&345&/&01DE&-&35!")); // 12ABC34501DE35
        System.out.println(CnpjUtils.normalize("&A12A.A345A.A678A/A0001A-A95A$")); // throws InvalidCnpjException
    }
}
```

### 🎨 Formatting

```java
import io.github.felseje.cpf.CpfUtils;
import io.github.felseje.cnpj.CnpjUtils;

public class Main {
    public static void main(String[] args) {
        // CPF formatter
        System.out.println(CpfUtils.format("01234567890")); // 012.345.678-90
        System.out.println(CpfUtils.format("O123456789O")); // throws InvalidCpfException

        // CNPJ formatter
        System.out.println(CnpjUtils.format("12ABC34501DE35")); // 12.ABC.345/01DE-35
        System.out.println(CnpjUtils.format("12ABC34501D&35")); // throws InvalidCnpjException
    }
}
```

### 📦 Model Usage

```java
import io.github.felseje.cpf.Cpf;
import io.github.felseje.cnpj.Cnpj;
import io.github.felseje.cnpj.CnpjType;

public class Main {
    public static void main(String[] args) {
        // CPF model
        new Cpf("123abc123oo"); // throws InvalidCpfException

        Cpf firstCpf = new Cpf("012.345.678-90");
        System.out.println(firstCpf.getBase()); // 012345678
        System.out.println(firstCpf.getCheckDigits()); // 90
        System.out.println(firstCpf.getValue()); // 01234567890
        System.out.println(firstCpf); // 012.345.678-90

        Cpf secondCpf = new Cpf("01234567890");
        System.out.println(firstCpf.equals(secondCpf)); // true
        System.out.println(firstCpf.hashCode() == secondCpf.hashCode()); // true

        // CNPJ model
        new Cnpj("$$.$$$.$$$/$$$$-00"); // throws InvalidCnpjException

        Cnpj firstCnpj = new Cnpj("12.ABC.345/01DE-35");
        System.out.println(firstCnpj.getRoot()); // 12ABC345
        System.out.println(firstCnpj.getOrder()); // 01DE
        System.out.println(firstCnpj.getCheckDigits()); // 35
        System.out.println(firstCnpj.getType()); // ALPHANUMERIC
        System.out.println(firstCnpj.getBase()); // 12ABC34501DE
        System.out.println(firstCnpj.getValue()); // 12ABC34501DE35
        System.out.println(firstCnpj.toString()); // 12.ABC.345/01DE-35

        Cnpj secondCnpj = new Cnpj("12ABC34501DE35");
        System.out.println(firstCnpj.equals(secondCnpj)); // true
        System.out.println(firstCnpj.hashCode() == secondCnpj.hashCode()); // true

        // CnpjType enum
        System.out.println(CnpjType.NUMERIC.matches("00000000000191")); // true
        System.out.println(CnpjType.NUMERIC.matches("00.000.000/0001-91")); // true
        System.out.println(CnpjType.ALPHANUMERIC.matches("12ABC34501DE35")); // true
        System.out.println(CnpjType.ALPHANUMERIC.matches("12.ABC.345/01DE-35")); // true

        System.out.println(CnpjType.detectFrom("00000000000191")); // NUMERIC
        System.out.println(CnpjType.detectFrom("00.000.000/0001-91")); // NUMERIC
        System.out.println(CnpjType.detectFrom("12ABC34501DE35")); // ALPHANUMERIC
        System.out.println(CnpjType.detectFrom("12.ABC.345/01DE-35")); // ALPHANUMERIC

        CnpjType.detectFrom("$$$ $$$ $$$ $$"); // returns an empty Optional

        System.out.println(CnpjType.NUMERIC.formattedPatternRegex()); // [0-9]{2}\.[0-9]{3}\.[0-9]{3}/[0-9]{4}-[0-9]{2}
        System.out.println(CnpjType.NUMERIC.unformattedPatternRegex()); // [0-9]{14}
        System.out.println(CnpjType.ALPHANUMERIC.formattedPatternRegex()); // [A-Z0-9]{2}\.[A-Z0-9]{3}\.[A-Z0-9]{3}/[A-Z0-9]{4}-[0-9]{2}
        System.out.println(CnpjType.ALPHANUMERIC.unformattedPatternRegex()); // [A-Z0-9]{12}[0-9]{2}
    }
}
```

---

## 📦 Package Structure

The library offers utility classes for fast generation, normalization, formatting, and validation of data, as well as
model classes tailored for use in specific domains.

```text
📦 io.github.felseje
 ┣ 📁 cpf
 ┃ ┣ 📄 Cpf.java
 ┃ ┗ 📄 CpfUtils.java
 ┣ 📁 cnpj
 ┃ ┣ 📄 Cnpj.java
 ┃ ┣ 📄 CnpjType.java
 ┃ ┗ 📄 CnpjUtils.java
```

---

## 📜 License

Distributed under the [BSD 3-Clause License](https://opensource.org/licenses/BSD-3-Clause). See the `LICENSE` file for
more details.

---

## 🤝 Contributing

Contributions are welcome! Feel free to open issues, pull requests or share suggestions.

---

> Crafted with 💻 and coffee by [@felseje](https://github.com/felseje) — Because CPF and CNPJ deserve proper care.