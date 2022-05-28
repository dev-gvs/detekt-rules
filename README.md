# Collection of Detekt rules

[![Badge]https://img.shields.io/jitpack/v/github/dev-gvs/detekt-rules?style=plastic](https://jitpack.io/#dev-gvs/detekt-rules)

A set of [Detekt](https://detekt.dev) rules to help prevent common errors in Android projects.

# Rules

Short summary of the rules in this rule set:

- `ModifierArgumentPosition` ensures that `modifier` argument is used as a last parameter for `@Composable` invocation


Rules can be individually turned `on` or `off` in the configuration file.

# Installation and configuration

Add detekt rules plugin in your `build.gradle` (or use any other [supported method](https://detekt.dev/docs/introduction/extensions#let-detekt-know-about-your-extensions)):
```
dependencies {
  detektPlugins("com.github.dev-gvs:detekt-rules:$version")
}
```
and then add this configuration section to your `detekt-config.yml` to activate the rules:
```
dev-gvs:
  active: true
  ModifierArgumentPosition:
    active: true
```

# License
```
MIT License

Copyright (c) 2022 Valentin Gusselnikov

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
