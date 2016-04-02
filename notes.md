
# Redirect with Controller

```
// Match everything without a suffix (so not a static resource)
@RequestMapping(value = "/{[path:[^\\.]*}")
public String redirect() {
    // Forward to home page so that route is preserved.
    return "forward:/";
}
```
