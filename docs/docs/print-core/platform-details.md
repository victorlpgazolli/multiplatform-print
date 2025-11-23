---
sidebar_position: 3
sidebar_label: Platform Details
---

# Platform Details

`printer-core` provides a unified API, but internally each platform implements
its own printing pipeline optimized for its native environment.

### File Conversion

Some file types (PNG, JPG, TXT, HTML) require conversion to PDF before printing.
The library does not convert to pdf, the OS should do this automatically.


## Android

On Android, printing uses the [`androidx.print.PrintHelper`](https://developer.android.com/reference/androidx/print/PrintHelper) API.  
The library handles:

- Convert `ImageBitmap` onto a `Bitmap` before sending to print  
- Automatically launching the system print dialog

---

## iOS

On iOS, the library uses:

- [`UIPrintInteractionController`](https://developer.apple.com/documentation/uikit/uiprintinteractioncontroller)
- [`UIPrintInfo`](https://developer.apple.com/documentation/uikit/UIPrintInfo)

Files are printed using the default iOS document pipeline.

---

## Desktop

On Desktop, printing is built using [Java Print Service (JPS)](https://docs.oracle.com/javase/8/docs/technotes/guides/jps/index.html)

---

## Web

> Printing is not supported on Web platform at the moment.