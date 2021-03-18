

#### 简单的层级差异mapper
```java
@Configuration
public class ModelConfig {
    private final ModelMapper modelMapper;

    public ModelConfig(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public ModelMapper createModelMapper() {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.addUModelMapping();
        return this.modelMapper;
    }

    private void addUModelMapping() {
        final TypeMap<Model, DTO> modelMap = this.modelMapper
            .createTypeMap(Model.class, DTO.class);

        modelMap.addMapping(model -> model.getSubObject().getField(),
              DTO::setField);
        modelMap.addMappings(mapper -> mapper.using(aConverter())
            .map(Model::getObjectList, DTO::setStringList));
    }

    private Converter<Collection<Object>, Collection<String>> aConverter() {
        return ctx -> {
            if (ctx.getSource() != null) {
                final Collection<EnvironmentCode> modelList = ctx.getSource();
                final Collection<String> stringList = new ArrayList<>();
                modelList.forEach( envCodeModel -> {
                    stringList.add(envCodeModel.getCode());
                });
                return estringList;
            }
            return null;
        };
    }
}
```

#### 提取对象数组的元素

