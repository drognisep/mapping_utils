# Mapping Utils

## Purpose

This is is a small lib intended to make mapping data between different Java objects declarative and painless.

## Initial Need

The initial use-case envisioned was for mapping DTOs encapsulating HTML form input to domain objects to be used to interact with the database. I got tired of typing out the transfer operations, and it made more sense to me to just declare it once using annotations and reflection as a reusable method of eliminating this step.
