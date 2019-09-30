

project id | pasa_cbentley_core_src4
------------ | -------------
author | Charles-Philip Bentley
license | ![license MIT](https://img.shields.io/badge/license-MIT-yellowgreen.svg?style=plastic)
tags | Java
created | June 2019
modified | ![GitHub last commit](https://img.shields.io/github/last-commit/cpbentley/pasa_cbentley_core_src4.svg?style=plastic)

Quote by Aldous Huxley
> There's only one corner of the universe you can be certain of improving, and that's your own self.

# Core Src4

This module is a minimum stripped down, CLDC API 1.1 compatible.

It is the basis for all cbentley __src4__ modules

For easier compatiblity with later Java releases, the module uses java_src4_compat_j2se.

This java_src4_compat_j2se will be ignored when building a release with later Java platforms.

# The Context Gods

This pattern provides a Ctx object for every module. If in the past you wanted to use static singletons,
now those singletons go to the context god of their module.

An application uses multiples modules, with a tree hierarchy of dependencies. Each module context god
will depend on other module and must get a reference to those module context deities.

The module context knows everything about the services provided by the module.

The advantage is that you can have several instances of the same module context with different parameters within
the same application. Static state has been eradicated by the godly benevolence of module contexts.

For easier code navigation, each god has several servants, smaller entities who specialize in a given domain for their
parent context.

The only static methods left are those methods that can be inlined. Small straight forward methods without any outside state. The choice of inlining be left to the byte code optimizer.

Increasing the size of arrays is potentially modifying application state in case of an OutofMemory. Thus any
method that increases the unknown size of an array must go through its god memory servant. Thus it cannot be static.

UCtx, Universal Context, the primary god from which all life flows, ERU, the nurturing Collector Shape . 

#### Github Repository Dependencies

num | id | Right Click -> Copy Link Address
----| -- | -------------
1 | java_src4_compat_j2se | [Import Link](https://github.com/cpbentley/java_src4_compat_j2se)
