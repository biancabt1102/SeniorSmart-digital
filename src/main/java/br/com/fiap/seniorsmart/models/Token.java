package br.com.fiap.seniorsmart.models;

public record Token(
    String token,
    String type,
    String prefix
) {}

