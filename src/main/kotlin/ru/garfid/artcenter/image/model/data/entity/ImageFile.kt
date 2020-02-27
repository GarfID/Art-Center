package ru.garfid.artcenter.image.model.data.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import ru.garfid.artcenter.core.model.entity.util.SimpleEntity
import javax.persistence.*

@Entity
@Table(name = "image_file")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
class ImageFile(
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "image_id", referencedColumnName = "id")
        @JsonBackReference
        val image: Image = Image(),
        @Lob
        val file: ByteArray = ByteArray(0)
) : SimpleEntity()