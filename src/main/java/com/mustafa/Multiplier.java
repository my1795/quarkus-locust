package com.mustafa;

import com.sun.management.UnixOperatingSystemMXBean;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;

import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/multiply")
public class Multiplier {

    private static final Logger LOG = Logger.getLogger(Multiplier.class);
    private AtomicInteger at = new AtomicInteger();
    @PostConstruct
    public void init(){
        final UnixOperatingSystemMXBean osMBean =
                (UnixOperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        LOG.debug("opendfs: " + osMBean.getOpenFileDescriptorCount());
        LOG.debug("maxfds:  " + osMBean.getMaxFileDescriptorCount());
    }
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @NonBlocking
    public Uni<Double> multiply(@QueryParam("num1") Double num1, @QueryParam("num2") Double num2) {
        final UnixOperatingSystemMXBean osMBean =
                (UnixOperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        at.incrementAndGet();
        LOG.debug("reqnum:  " + at.get());
        return Uni.createFrom().item(num1*num2);
    }
}
