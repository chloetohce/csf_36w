# Notes -- CSF Day 36 Workshop

## File upload
Cannot use `formControlName` on `input:file`. Need to programmatically set the form data to the file. Will get:
```txt
InvalidStateError: Failed to set the 'value' property on 'HTMLInputElement': This input element accepts a filename, which may only be programmatically set to the empty string.
```

### Method 1
1. 