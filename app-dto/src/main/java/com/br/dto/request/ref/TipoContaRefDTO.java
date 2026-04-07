package com.br.dto.request.ref;

import java.math.BigInteger;
import java.util.UUID;

public record TipoContaRefDTO(
    BigInteger id,
    UUID uuid
){}
