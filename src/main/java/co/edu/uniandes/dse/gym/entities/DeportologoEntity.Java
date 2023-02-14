public class DeportologoEntity extends BaseEntity {
    private String nombre;
    private String login;
    private String experiencia;
    private String foto;
    @OneToOne
    public SedeEntity sede;
    @OneToMany(mappedBy = "deportologo")
    public ArrayList<AtletaEntity> valoracionAtletas;
}