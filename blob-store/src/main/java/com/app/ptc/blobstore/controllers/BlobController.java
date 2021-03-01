package com.app.ptc.blobstore.controllers;

import com.app.ptc.blobstore.model.ApplicationProperties;
import com.app.ptc.blobstore.model.BlobImage;
import com.app.ptc.blobstore.model.BlobStoreRequest;
import com.app.ptc.blobstore.repositories.BlobRepository;
import com.google.common.collect.ImmutableMap;
import org.apache.tomcat.util.buf.HexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/blob")
public class BlobController {
    private static byte[] imageContents = HexUtils.fromHexString("89504e470d0a1a0a0000000d49484452000000e1000000e10803000000096d22480000008a504c5445ffffff000000e4e4e4e0e0e0878787edededb8b8b8d0d0d0a9a9a9ccccccf6f6f6c8c8c89c9c9cc5c5c5d3d3d35c5c5cafafaf4f4f4f8080801f1f1fbebebe919191151515626262f8f8f80d0d0d727272efefefe8e8e8888888d9d9d97a7a7a969696a2a2a22727274a4a4a6b6b6b2e2e2e4141413535352424243b3b3b5e5e5e4d4d4d1a1a1a121212f60cd2ff0000088449444154789ced9d0b77a2bc16400928c853b45a1110c4f1d171eaffff7b97c47aab92400841c0efecd5b5a6c3c8630be4717292511400000000000000000000000000000000000000000000000000e81ecfb519b85ed7d726873f88c9b1eb6b93c38c6d38ebfadae4f0df30f44dd3b64df2f25d7f314de7bd0c5525509e7ecc3733f491e1223d41918ad40c990ba41b6f6638c156be87c61ad21c64ebc807c3010186c3070c870f180e1f301c3e60387cc070f8fc18da0b6c753574dfcb709aab688913ab4e327682c8893c27d61cadd73de0791d463bd2c72fd0eb7bc80e2db198ae0acc9a1a867b593e45ea1b326862e820144a337aa60f863ade7f25cfe9117c70b780bdf846686217b69b9f08f985cd76b368a279fd8a8e9144ad3bf0b1299bad8fbcd0a46ca76f6e549646b7a760473b617388618cc619f274b43050965772f1c5c086aa833c1fb926f25534b690aa2343f926d144f3164dd4244413efe2e8894cb31bc45045b1830c1de92672b0213289e1121b2e6c3421865a6e460ce5b669f4fb97b90dc55f43afca506fc5f0805a56ecdaf0e11622f41dcbd553ba37fcf754e91c02b97e9d1bc6857a752a59b06bc349b1e990bd97e1b96848ad87876b4811949d11d0ada1463344fe1b19da54435a3372a886948206e3bc8fe1866e28f526766b18320cf5b7315c310c65067d7e0d0d1f1b6e1f0c8d0936cc7b54d7be85fd6368df19368a26d2aa4382c4e6e91a1b46a9e5a6aa977a6aea8ee791355789a13b8f8d34d15233fff7201d1ba9460cf33f93d488e7d6388d6cfcbb27df50a0c2b046f4f820e3bd0ebea86143dcc767441377f41394178baca754243035661d2b674a21bfb7b37361eb8abaf95c92b2f8a7f4aa52d66ebb5b1723529344b3780ca3cf12c516297f7897ccfd02c5729df3faf6d7cf555695c0da4f439fb95ffadc71cc09ddb2ce23365c67137ff2fc93ef49d99ce51f5ffa85cdfe85b6d9cfdfe64fbf78906da5a159f3fb3a6cc7e5868192df6a43898cc0322cc518e7bfe3eae2775b6044f8239e12e02225f214cb08f036e3e723a4a421dbacebe7aebbe525cd978277f7ee0f45bab7e586f496772973d64b490cf34b51639464c8cd2bb4046dadbcde41d7fa708beb43df46db04d78736a92d36b83ed4dde7fa30c3f5e1e43abab645ae8f7f471ff913e79b245f714b02931e3e6c6525b2ae12a2b0a53fab57430fc5b9615e659380a77367d8b44d430c0d6c880fb5406ec265589274cce66b3120c34cc4901ecbe9a9a12a64488daaf6d4f02920cc077db0b1af868e8021ad39d95fc3b2d624830dfd487d3554e675050f8c03f5d6b018f5ae803578d35b4325ab27c80c89f7d7b06cba581176c7b1c786d637bf60c9b84d7f0dcd1af7f05fc971fa6ae8d66999968ebcf5d2309a7cd4f043cbd2efead67b8a49efc926bda747c36b34f1d1d0f079a3898f863cbda7d86746daa87cbaa5823f866a1845616c875a121a71b808f611318cc2c80d5523f4b4d08dc371946ac47011aa669868a13d0ead603f36c26b34d1c4bb9b6a688dc3b11b6a5e6810432334b4d0ba1eca0b1375cf360c92455aab04cd9957251445f9b55d685d47448f26d2c386ec68e217e5c3cf7d7c4b4dcc85bf5c9deac78cf64f67ddb2be87cde8193c36322a6cdecc2f79d553fcf432ef93a7c54feff36f8f726866acb0268765213ac31ad21922b38d41793c647d79ddf277b5c93cc6dbc70c960f8199120495698a75cba95e410d333d73eafa2a9bc023a8d46a29f48c2d976147631452e01a67128a24f7843997a0bcf4f4d7c399f2d5f5658ac33befa2ebeb14873747b8ebeb14e6cc2938dc92863bf1b24668a757f0cf9bd9757da982f0e7ce1ebbbe5431526e4132117280d448f5aa3ddad10bca43698f880cca750e358ac4829d88d3636ae594d64dc4e903f5721ba3ea03f68e7ab32c82e1356aea143398c15517aca16b26830b98d69e77c898a9d15b387bf677d41efeef96536d41c18cb8ce10991b9b757dd175109a7128909ada1982b3468713f65e0bcefccd1a9cf3cbd127af1bfa105d44da123fe5881c6021cfa19489a0a0a2ec454f192a93e929afa03c991e4c6af4eb9f11be4005cf14c2e3aeece91ef2289f52538160953827d1d68512242f68367c355a5f40f0459a5c6ffe711dd6c93713634dcff6e5e6afd059c96442cc2a687d90aee95afc7af529282c6fa5f0b6f5a7d46c282858eb5f6ed31ea3b64b1a09937dc5fa50a9925ef242ce4b240b3d23655d01b1e2340d142b68bdc697b3708268fb7bba09db2e4765ad0cf18a4a5b087913ee4526ddbc00aeac203e5ed3b8ac8bd4ff93863fea7698bfe8867f485eed8a672cf174deb82a73a511c9cc64af1e5832c3ef72fab71f6df564fcd3feb55e912dd6c22297a4f1c6d7407ac14d94bdce1581bc8a7ca557db6fe277f3a628159250cb55c5b61c2b97fe0ade08483f8a6b74a7de14889a485d1ce9118ba49ff0e48c35086055716865edce1bd1059fe30f4745d45a837bd4a65f4e7cedaf574cacc1b493c77168a988b957bc7616385ec63662c1dbb6f530d6b52638558e975bd2c33333f98b9252097efac39575aee4519d93c48e44153f53698e554d7ba91d92561a314c6e9944694520565e8866c9977a2f0fedd62c73ca1d252956ce196c01eb36e96be7973a6a1252548b73ea5ec3ff9b9ebbd237246a9aa39abdfaf9bce3b74e2ffd969b4cf13bca5cda5100ed37656a6fb3bf6ab1510184d669ab2d503eecdf67703762561eb1484479aa7750bcd070ef0635be46ac5663dd4eff29eba874a1623cf405a799462b5cad1a83e5c7ace170a07cd4e543d4627ddeb85ae111e35bb6e27bafbfa8f15993c07c2e31bf8fab8def7aea5d0154e93875920eab866adc11bd33b1fe38fcfd339bcda6e7333b21f732cd6893c97b47e2afea8fa61e578edbbb17af844075377bbe60e2c72c5c2eb47ebe76550496a6674bc6fddce5628e6fc696f4ff81a21bac58f30cc3b60d2fd1d49ed4e10000000000000000000000000000000000000000000000000020c2ff00db090003d6975ebd0000000049454e44ae426082");
    private final ApplicationProperties properties;

    private final BlobRepository repository;

    Logger logger = LoggerFactory.getLogger(BlobController.class);

    /**
     * Constructor
     *
     * @param properties
     * @param repository
     */
    public BlobController(ApplicationProperties properties, BlobRepository repository) {
        this.properties = properties;
        this.repository = repository;
    }

    /**
     * GET /api/v1/blob/{id}
     * <p>
     * API to download image.
     *
     * @param response
     * @param id
     * @throws IOException
     */
    @RequestMapping("/{id}")
    @GetMapping
    public void downloadPDFResource(HttpServletResponse response,
                                    @PathVariable("id") Long id) throws IOException {
        try {
            logger.info(String.format("Got a request to download %s", id));

            Optional<BlobImage> blobImageOptional = repository.findById(id);
            BlobImage blob = blobImageOptional.orElseThrow(() -> new IllegalArgumentException("File not found!"));
            logger.info(String.format("The request translated to the file by name: %s", blob.getFileNameWithAbsolutePath()));
            Path file = Paths.get(blob.getFileNameWithAbsolutePath());

            String contentType = MediaType.ALL_VALUE;

            logger.info(String.format(String.format("Content Type would be %s", contentType)));

            if (Files.exists(file)) {
                response.setContentType(contentType);
                response.addHeader("Content-Disposition", "attachment; filename=" + blob.getFileNameWithAbsolutePath());
                try {
                    response.getOutputStream().write(imageContents);
                    response.getOutputStream().flush();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());

            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * POST /api/v1/blob
     * <p>
     * API to upload image.
     *
     * @param request
     * @return
     */

    @PostMapping
    public ResponseEntity<BlobImage> saveImage(@RequestBody BlobStoreRequest request) {
        logger.info("Got a request to save an image: {}", request.getMd5());
        BlobImage blob = BlobImage.builder()
                .build();
        blob = repository.save(blob);

        String storageLocation = properties.getStorageLocation();
        String uploadDir = storageLocation + "/" + blob.getId();

        blob.setImageUri(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/blob/{id}")
                .buildAndExpand(blob.getId()).toUriString());

        blob.setFileNameWithAbsolutePath(uploadDir);

        repository.save(blob);
        return ResponseEntity.ok(blob);
    }
}
