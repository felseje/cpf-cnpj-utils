# Changelog

All notable changes to this project will be documented in this file.

This project adheres to [Keep a Changelog](https://keepachangelog.com/en/1.0.0/)
and uses [Semantic Versioning](https://semver.org/).

### [Unreleased]
#### Chore
- Move GPG signing to deploy phase to prevent CI failures
- Add CI workflow to run tests on main and PRs

### [v1.0.0-alpha] - 2025-07-15
#### Changed
- Changed `pom`.

### [v0.15.0-SNAPSHOT] - 2025-07-01
#### Added
- Created `CpfTest`
- Created `CpfUtilsTest`
- Created `CnpjTest`
- Created `CnpjTypeTest`
- Created `CnpjUtilsTest`

### [v0.14.0-SNAPSHOT] - 2025-06-21
#### Added
- Created `module-info` file
- Created `package-info` files

### [v0.13.0-SNAPSHOT] - 2025-06-06
#### Added
- Created `InvalidDocumentException`
- Created `InvalidDocumentBaseException`
#### Changed
- Changed `InvalidCpfException`.
- Changed `InvalidCpfBaseException`.
- Changed `InvalidCnpjException`.
- Changed `InvalidCnpjBaseException`.
- Changed `UnrecognizedTypeException` to `UnrecognizedCnpjTypeException`.

### [v0.12.1-SNAPSHOT] - 2025-05-30
#### Removed
- Removed `AllTestRunner`.

### [v0.12.0-SNAPSHOT] - 2025-05-30
#### Added
- Created `AllTestRunner`.
#### Changed
- Changed `README.md`.

### [v0.11.0-SNAPSHOT] - 2025-05-30
#### Added
- Created `Cpf`.
- Created `CpfUtils`.
#### Changed
- Changed `CpfValidator`.
- Changed `CpfNormalizer`.

### [v0.10.0-SNAPSHOT] - 2025-05-27
#### Added
- Created `CpfNormalizer`
- Created `CpfFormatter`
- Created `InvalidCpfException`

### [v0.9.0-SNAPSHOT] - 2025-05-26
#### Added
- Created `CpfGenerator`
- Created `CpfValidator`

### [v0.8.0-SNAPSHOT] - 2025-05-18
#### Added
- Created `Integers`.
- Created `CpfCheckDigitCalculator`.
- Created `InvalidCpfBaseException`.

### [v0.7.0-SNAPSHOT] - 2025-05-14
#### Added
- Created `Constants`.
#### Changed
- Changed `CnpjUtils`.
- Changed `Characters`.
- Changed `StringUtils`.
- Changed `CnpjCheckDigitCalculator`.

### [v0.6.0-SNAPSHOT] - 2025-05-14
#### Added
- Created `Cnpj`.
- Created `CnpjUtils`.
#### Changed
- Changed `AbstractValidator`
- Changed `CnpjNormalizer`

### [v0.5.0-SNAPSHOT] - 2025-05-13
#### Added
- Created `InvalidCnpjException`.
- Created `CnpjClassifier`.
- Created `UnrecognizedTypeException`.
- Created `CnpjNormalizer`.
- Created `CnpjFormatter`.

### [v0.4.0-SNAPSHOT] - 2025-05-12
#### Added
- Created `NumericValidator`.
#### Changed
- Changed `AbstractValidator`.

### [v0.3.0-SNAPSHOT] - 2025-05-11
#### Added
- Created `AbstractValidator`.
- Created `AlphanumericValidator`.

### [v0.2.0-SNAPSHOT] - 2025-05-10
#### Added
- Created `NumericGenerator`.
#### Changed
- Changed `AbstractGenerator`.

### [v0.1.0-SNAPSHOT] - 2025-05-09
#### Added
- Created `CnpjType`.
- Created `InvalidCnpjBaseException`.
- Created `Characters`.
- Created `CnpjCheckDigitCalculator`.
- Created `AbstractGenerator`.
- Created `AlphanumericGenerator`.

### [v0.0.0-SNAPSHOT] - 2025-05-07
#### Added
- Initial project package structure defined.
- Created `Classifier`.
- Created `Formatter`.
- Created `Normalizer`.
- Created `StringUtils`.
- Created `pom.xml`.
- Created `.gitignore`.
- Created `README.md`.
- Created `LICENSE.md`.
- Created `CHANGELOG.md`.