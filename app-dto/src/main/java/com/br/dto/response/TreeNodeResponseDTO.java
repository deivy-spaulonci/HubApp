package com.br.dto.response;

import java.util.List;

public class TreeNodeResponseDTO {
    public String label;
    public String data;
    public String icon;
    public List<TreeNodeResponseDTO> children;
    public Boolean expanded;

    public TreeNodeResponseDTO(String label, String data, String icon) {
        this.label = label;
        this.data = data;
        this.icon = icon;
        this.expanded = true;
    }
}
