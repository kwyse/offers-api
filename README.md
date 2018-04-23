# Offers API

| Service | Status |
| --- | --- |
| Travis CI | [![Build Status](https://travis-ci.org/kwyse/offers-api.svg?branch=master)](https://travis-ci.org/kwyse/offers-api) |
| Codecov | [![codecov](https://codecov.io/gh/kwyse/offers-api/branch/master/graph/badge.svg)](https://codecov.io/gh/kwyse/offers-api) |
| Codebeat | [![codebeat badge](https://codebeat.co/badges/1deb8428-d463-431b-809e-cd14f5d7a19b)](https://codebeat.co/projects/github-com-kwyse-offers-api-master) |

## Getting Started

The project runs against Gradle 4.6 and JDK 10.

```bash
# To build and run the tests
$ gradle build

# To launch the server locally
$ java -jar build/libs/offers-api.jar
```

## Design

The API is intended to model the [API specification](https://kwyse.github.io/offers-api/).
Offers are persisted to H2. Authentication concerns are ignored to keep things
simple.

New discount types can be added by implementing the `Discount` interface.

## Future improvements

1. Non-GET endpoints should return JSON payloads indicating status, as per the
API specification.
